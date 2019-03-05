package com.zemian.toolbox.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zemian.toolbox.AppException;
import com.zemian.toolbox.codegen.Config;
import com.zemian.toolbox.codegen.TemplateContext;
import com.zemian.toolbox.codegen.plugins.FtlTemplatePlugin;
import com.zemian.toolbox.codegen.plugins.Plugin;
import com.zemian.toolbox.codegen.plugins.StaticFilePlugin;
import com.zemian.toolbox.support.CmdOpts;
import com.zemian.toolbox.support.ConsumerEx;
import com.zemian.toolbox.support.JavaUtils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Zemian Deng on 2017
 */
public class CodeGen implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(CodeGen.class);

    public void printExitHelp() {
        System.out.println("A source code generator tool.\n" +
                "\n" +
                "This tool will walk through all the files in a template set directory (inside a classpath), and\n" +
                "then delegate all the processing works to one or more plugins. Each plugin can provide their own\n" +
                "logic to generate anything they wish based on the input template. Most of the built-in plugins can\n" +
                "be triggered by one of the option provided.\n" +
                "\n" +
                "Usage: CodeGen [options] [templateSetName]\n" +
                "\n" +
                "A 'templateSetName' is just a folder name under the <templatesDir>. Currently we provide\n" +
                "the following built-in set:\n" +
                "  javaapp - A maven based Java application.\n" +
                "  mavenapp - A multi modules maven application.\n" +
                "  springapp - A Spring command line application.\n" +
                "  jspwebapp - A JSP based web application.\n" +
                "  springbootapp - A Spring Boot application.\n" +
                "  servletwebapp - A Servlet based web application.\n" +
                "  springwebapp - A Spring web application.\n" +
                "  springwebappapi - A Spring web application for RESTful API.\n" +
                "\n" +
                "  --optionsFile=FILE\n" +
                "      A JSON file to supply all the command options in a map.\n" +
                "  --templateSetName=NAME\n" +
                "      A sub directory name inside <templateDir> to generate code. Default to arguments[0]>.\n" +
                "  --projectName=NAME\n" +
                "      Project name to generate. Default to <templateSetName>.\n" +
                "  --outputDir=DIR\n" +
                "      Output directory. Default 'target/<projectName>'.\n" +
                "  --overrideFiles=true|false\n" +
                "      Override existing files in outputDir.\n" +
                "      Default to false.\n" +
                "  --templatesDir=PATH\n" +
                "      A path in CLASSPATH to find FTL templates.\n" +
                "      Default 'toolbox/templates' that's from the dist jar.\n" +
                "  --plugins=CLASSNAME1,CLASSNAME2...\n" +
                "      A csv list of classname of plugins to use. We provide a default set, and you can\n" +
                "      add more custom one. See [BUILT-IN PLUGINS] section for more details.\n" +
                "\n" +
                "BUILT-IN PLUGINS:\n" +
                "\n" +
                getPluginsHelps() +
                "");
        System.exit(1);
    }

    public String getPluginsHelps() {
        StringBuilder sb = new StringBuilder();
        for (Plugin plugin : plugins) {
            sb.append("Plugin: " + plugin.getName() + "\n");
            sb.append(plugin.getHelp());
            sb.append("\n");
        }
        return sb.toString();
    }

    private CmdOpts opts;
    private List<Plugin> plugins = new ArrayList<>();

    private String projectName;
    private String templateSetName;
    private String templatesDir;
    private boolean overrideFiles;
    private File outputDir;
    private Configuration freeMarkerConfig;
    private Map<String, Object> templateModel = new HashMap<>();
    private Map<String, String> templatePathMappings = new HashMap<>();
    private Map<String, String> dbDataTypeToJavaTypes = new HashMap<>();
    private ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public Map<String, String> getDbDataTypeToJavaTypes() {
        return dbDataTypeToJavaTypes;
    }

    public Map<String, String> getTemplatePathMappings() {
        return templatePathMappings;
    }

    public CmdOpts getOpts() {
        return opts;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public String getTemplateSetName() {
        return templateSetName;
    }

    public String getTemplatesDir() {
        return templatesDir;
    }

    public boolean isOverrideFiles() {
        return overrideFiles;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public Configuration getFreeMarkerConfig() {
        return freeMarkerConfig;
    }

    public Map<String, Object> getTemplateModel() {
        return templateModel;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public void run(CmdOpts opts) throws Exception {
        this.opts = opts;

        // == Build up a list of plugins to use
        // We always enable this static file copy plugin in.
        LOG.debug("Loading plugin class: {}", StaticFilePlugin.class);
        plugins.add(new StaticFilePlugin());

        // Now we can take user custom plugin list or use a default list.
        String pluginsList = opts.getOpt("plugins", "JavaProjectPlugin,DbTableToJavaPlugin");
        for (String pluginClassName : pluginsList.split(",")) {
            // Auto add package prefix if there is none.
            if (pluginClassName.indexOf(".") < 0) {
                pluginClassName = "com.zemian.toolbox.codegen.plugins." + pluginClassName;
            }

            // Load it
            LOG.debug("Loading plugin class: {}", pluginClassName);
            Class<?> clazz = Class.forName(pluginClassName);
            if (!Plugin.class.isAssignableFrom(clazz)) {
                throw new AppException("Plugin " + clazz + " is not of type com.zemian.toolbox.codegen.CodeGenPlugin");
            }
            plugins.add((Plugin) clazz.newInstance());
        }

        // We always enable this ftl template plugin in.
        // This needs to add last to ensure all other plugin gets to run first.
        LOG.debug("Loading plugin class: {}", FtlTemplatePlugin.class);
        plugins.add(new FtlTemplatePlugin());


        // == Parse optionsFile if given
        objectMapper = createObjectMapper();
        if (opts.hasOpt("optionsFile")) {
            String optionsFile = opts.getOpt("optionsFile");
            TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
            Map<String, Object> optionsMap = objectMapper.readValue(new File(optionsFile), typeRef);

            // NOTE: The optionsFile should be base option, and user options should able to override it
            // Therefore we will let user options to override optionsFile values.
            Map<String, Object> userOpts = opts.getOpts();
            optionsMap.putAll(userOpts);
            opts.setOpts(optionsMap);
        }

        // == Print help if given and exit
        // This must be call after all plugin is found.
        if (opts.hasOpt("help")) {
            printExitHelp();
        }

        // == Get templateSetName from arguments
        this.templateSetName = opts.getOpt("templateSetName", null);
        this.templatesDir = opts.getOpt("templatesDir", "toolbox/templates");

        if (templateSetName == null) {
            templateSetName = opts.getArgOrError(0, "Missing templateSetName argument.");
        }

        // == Load templateSet config if there is any
        String configRes = templatesDir + "/" + templateSetName + "/config.json";
        try (InputStream configStream = getClassLoader().getResourceAsStream(configRes)) {
            if (configStream != null) {
                Config config = objectMapper.readValue(configStream, Config.class);

                if (config.getTemplateModel() != null) {
                    // Add global template model if there is any
                    this.templateModel.putAll(config.getTemplateModel());
                }
                if (config.getTemplatePathToOutputFiles() != null) {
                    this.templatePathMappings.putAll(config.getTemplatePathToOutputFiles());
                }

                if (config.getDbDataTypeToJavaTypes() != null) {
                    dbDataTypeToJavaTypes.putAll(config.getDbDataTypeToJavaTypes());
                }
            }
        }

        // == Start main process of this tool
        this.projectName = opts.getOpt("projectName", templateSetName);
        this.outputDir = new File(opts.getOpt("outputDir", "target/" + projectName));
        this.overrideFiles = opts.getBooleanOpt("overrideFiles", false);
        this.freeMarkerConfig = createFreeMarkerConfig(templatesDir);

        // Ensure output directory exists.
        if (!outputDir.exists()) {
            LOG.debug("Creating new outputDir={}", outputDir);
            outputDir.mkdirs();
        } else {
            LOG.debug("Using outputDir={}", outputDir);
        }

        // Init global templateModels for all templates
        templateModel.put("todayDt", new Date());
        templateModel.put("templateSetName", templateSetName);
        templateModel.put("templatesDir", templatesDir);
        templateModel.put("projectName", projectName);
        templateModel.put("outputDir", outputDir);

        // Init ALL plugins
        for (Plugin plugin : plugins) {
            LOG.debug("Initializing plugin={}", plugin);
            plugin.init(this);
        }

        // Process only enabled plugins from this point onward.
        List<Plugin> enabledPlugins = plugins.stream().filter(p -> p.isEnabled()).collect(Collectors.toList());

        // Before Generate Event
        for (Plugin plugin : enabledPlugins) {
            LOG.debug("Before generate event plugin={}", plugin);
            plugin.beforeTemplateSet();
        }

        // Gather all templates and run plugin through it
        LOG.info("Generating templateSetName={}, overrideFiles={}", templateSetName, overrideFiles);
        processTemplateSet(templateContext -> {
            for (Plugin plugin : enabledPlugins) {
                LOG.debug("Processing {} by plugin={}", templateContext, plugin);
                plugin.generate(templateContext);
            }
        });

        // After Generate Event
        for (Plugin plugin : enabledPlugins) {
            LOG.debug("After generate event plugin={}", plugin);
            plugin.afterTemplateSet();
        }

        // Destroy ALL plugins
        for (Plugin plugin : plugins) {
            LOG.debug("Destroying plugin={}", plugin);
            plugin.destroy();
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper ret = new ObjectMapper();
        ret.enable(SerializationFeature.INDENT_OUTPUT);
        return ret;
    }

    public void processTemplateSet(ConsumerEx<TemplateContext> consumer) throws Exception {
        JavaUtils.walkClasspathResDir(getClassLoader(), templatesDir + "/" + templateSetName, (fileStream) -> {
            TemplateContext templateContext = new TemplateContext();
            templateContext.setTemplateFileStream(fileStream);
            consumer.accept(templateContext);
        });
    }

    private Configuration createFreeMarkerConfig(String templateLoaderPath) {
        ClassLoader cl = getClassLoader();
        ClassTemplateLoader loader = new ClassTemplateLoader(cl, templateLoaderPath);

        Configuration config = new Configuration(Configuration.VERSION_2_3_27);
        config.setTemplateLoader(loader);
        return config;
    }

    private ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public InputStream getTemplateStream(String templatePath) {
        InputStream result = getClassLoader().getResourceAsStream(templatePath);
        if (result == null) {
            throw new AppException("Template resource " + templatePath + " not found.");
        }
        return result;
    }

    public File getOutputFile(String templatePath, Map<String, Object> templateModel) throws Exception {
        String outputFileName = templatePathMappings.get(templatePath);
        if (outputFileName != null) {
            StringReader reader = new StringReader(outputFileName);
            Template template = new Template("outputFile", reader, freeMarkerConfig);
            StringWriter writer = new StringWriter();
            template.process(templateModel, writer);
            outputFileName = writer.toString();
        } else {
            outputFileName = templatePath;
        }
        return new File(outputDir + "/" + outputFileName);
    }

    public void writeOutputFile(File outputFile,
                                String templatePath,
                                InputStream templateStream,
                                Map<String, Object> templateModel) throws Exception {
        // Ensure output file and dir is ready for write new file.
        if (!overrideFiles && outputFile.exists()) {
            return;
        }

        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        String outputPath = outputFile.getPath();
        LOG.info("Generating template file from {} to {}", templatePath, outputPath);
        try (Reader reader = new InputStreamReader(templateStream)) {
            Template template = new Template(templatePath, reader, freeMarkerConfig);
            try (FileWriter writer = new FileWriter(outputFile)) {
                template.process(templateModel, writer);
            }
        }
    }

    public void writeOutputFile(String outputFileName,
                                 String templatePath,
                                 Map<String, Object> templateModel) throws Exception {
        File outputFile = new File(outputDir, outputFileName);
        try (InputStream templateStream = getTemplateStream(templatePath)) {
            writeOutputFile(
                    outputFile,
                    templatePath,
                    templateStream,
                    templateModel);
        }
    }

    public void writeJavaOutputFile(String packageName,
                                     String outputPath,
                                     String outputName,
                                     String templatePath,
                                     Map<String, Object> templateModel) throws Exception {

        boolean removeKey = !(templateModel.containsKey("packageName"));
        templateModel.put("packageName", packageName);
        String pkgPath = packageName.replaceAll("\\.", "/");
        String outputFileName = outputPath + "/" + pkgPath + "/" + outputName;

        File outputFile = new File(outputDir, outputFileName);
        try (InputStream templateStream = getTemplateStream(templatePath)) {
            writeOutputFile(
                    outputFile,
                    templatePath,
                    templateStream,
                    templateModel);
        }

        if (removeKey) {
            templateModel.remove("packageName");
        }
    }
}
