package com.zemian.toolbox.codegen.plugins;

import com.zemian.toolbox.AppException;
import com.zemian.toolbox.codegen.model.Controller;
import com.zemian.toolbox.codegen.model.DAO;
import com.zemian.toolbox.codegen.model.DbField;
import com.zemian.toolbox.codegen.model.DbTable;
import com.zemian.toolbox.codegen.model.Domain;
import com.zemian.toolbox.codegen.model.Field;
import com.zemian.toolbox.codegen.model.Service;
import com.zemian.toolbox.support.CmdOpts;
import com.zemian.toolbox.support.JavaUtils;
import com.zemian.toolbox.support.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This plugin requires ProjectPlugin, and the templateSetName must contains the word "api" or not.
 * This plugin will by default cache the database metadata in to file system. You may use flag
 * to force a refresh fo the cache, or to disable it.
 */
public class DbTableToJavaPlugin extends AbstractPlugin {
    private static final Logger LOG = LoggerFactory.getLogger(DbTableToJavaPlugin.class);

    @Override
    public String getHelp() {
        return "Generate Java classes by inspecting DB tables.\n" +
                "  --dbTables=TABLE_NAME1,TABLE_NAME2... \n" +
                "      Generate DB table to Java code. Table name can use '%' as wildcard pattern.\n" +
                "      Required option and will enable this plugin.\n" +
                "  --tableToDomainNames=TABLE_NAME1=DOMAIN_NAME1,TABLE_NAME2=DOMAIN_NAME1... \n" +
                "      Explicitly map table name to domain class name.\n" +
                "      Default to empty, which to derive from table name.\n" +
                "  --colEnumList=TABLE_NAME.COLUMN,Value1,Value2...\n" +
                "      Generate Java enum values for mapped table column in a domain field.\n" +
                "      This option can be use multiple times.\n" +
                "  --colKey=TABLE_NAME.COLUMN\n" +
                "      If key column is not auto incremented/genearted, then use this option to explicitly\n" +
                "      name DB column as key for the domain model.\n" +
                "      This option can be use multiple times.\n" +
                "  --useSingularDomain=true|false\n" +
                "      When converting from DB table name to Java domain classes, drop plural ending and\n" +
                "      use singular form of English word for domain name instead.\n" +
                "      Default true.\n" +
                "  --controllerType=API|UI\n" +
                "      Generate API controllers or Web UI controllers, or both.\n" +
                "      Default based on <templateSetName> contains 'api' or not.\n" +
                "  --apiUrlMapping=URL\n" +
                "      If controllerType is API, this url is used to prefix to all controller\n" +
                "      url mapping for all actions.\n" +
                "      Default '/api'.\n" +
                "  --webUiUrlMapping=URL\n" +
                "      If controllerType is UI, this url is used to prefix to all controller\n" +
                "      url mapping for all actions.\n" +
                "      Default '/admin'.\n" +
                "  --dbTableTemplateDir=PATH\n" +
                "      Path to this plugin templates, relative to <templateDir>.\n" +
                "      Default to 'plugins/dbtable'\n" +
                "  --dbUrl=URL         Default 'jdbc:postgresql://localhost/test'.\n" +
                "  --dbDriver=NAME     Default 'org.postgresql.Driver'.\n" +
                "  --dbUser=USER       Default 'test'.\n" +
                "  --dbPass=PASSWORD   Default empty.\n" +
                "  --dbTablesFromExport=true|false\n" +
                "      Use exported JSON data instead of reading database for metadata.\n" +
                "      Default 'false'\n" +
                "  --export=true|false\n" +
                "      Export all database metata into <exportDir>, and do not generate code.\n" +
                "      Default 'false'\n" +
                "  --exportDir=DIR\n" +
                "      Save and cache the database metadata.\n" +
                "      Default '<outputDir>/target/toolbox/dbtable'\n" +
                "";
    }

    private String dbTableTemplateDir;
    private List<DbTable> dbTables = new ArrayList<>();

    // Key table name, Value = Map (key = Column Name, Values = List of enum values)
    private Map<String, Map<String, List<String>>> enumValuesByTable = new HashMap<>();
    private Map<String, String> keyFieldByTable = new HashMap<>();
    private Map<String, String> tableToDomainNames = new HashMap<>();

    private String exportDir;
    private boolean export;
    private boolean dbTablesFromExport;
    public static final String EXPORT_FILE_EXT = ".json";

    @Override
    public boolean isEnabled() {
        return codeGen.getOpts().hasOpt("dbTables");
    }

    @Override
    public void init() throws Exception {
        if (!isEnabled()) {
            return;
        }

        CmdOpts opts = codeGen.getOpts();
        Map<String, Object> templateModel = codeGen.getTemplateModel();

        // == Init DB parameters
        String dbDriver = opts.getOpt("dbDriver", "org.postgresql.Driver");
        String dbUrl = opts.getOpt("dbUrl", "jdbc:postgresql://localhost/test");
        String dbUser = opts.getOpt("dbUser", "postgres");
        String dbPass = opts.getOpt("dbPass", "");
        templateModel.put("dbDriver", dbDriver);
        templateModel.put("dbUrl", dbUrl);
        templateModel.put("dbUser", dbUser);
        templateModel.put("dbPass", dbPass);
        LOG.debug("Using DB dbUrl={}, dbDriver={}, dbUser={}", dbUrl, dbDriver, dbUser);

        // == Init dbTableTemplateDir and tableToDomainNames
        dbTableTemplateDir = opts.getOpt("dbTableTemplateDir", "plugins/dbtable");
        if (opts.hasOpt("tableToDomainNames") ) {
            String[] pairs = opts.getOpt("tableToDomainNames").split(",");
            for (String pair : pairs) {
                String[] words = pair.split("=");
                tableToDomainNames.put(words[0], words[1]);
            }
        }

        // == Init export settings
        exportDir = opts.getOpt("exportDir", codeGen.getOutputDir() + "/target/toolbox/dbtable/");
        export = opts.getBooleanOpt("export", false);
        dbTablesFromExport = opts.getBooleanOpt("dbTablesFromExport", false);

        // == Parse enum values list map
        List<String> enumList = opts.getMultiOpts("colEnumList");
        for (String enumStr : enumList) {
            String[] parts = enumStr.split(",");
            String[] tableParts = parts[0].split("\\.");
            String tableName = tableParts[0];
            String colName = tableParts[1];
            List<String> values = new ArrayList<>(Arrays.asList(parts));
            values.remove(0);
            values.stream().map(e -> e.toUpperCase()).collect(Collectors.toList());

            Map<String, List<String>> enumColumns = enumValuesByTable.get(tableName);
            if (enumColumns == null) {
                enumColumns = new HashMap<>();
                enumValuesByTable.put(tableName, enumColumns);
            }
            enumColumns.put(colName, values);
        }

        // == Parse explicit table key values
        List<String> keyList = opts.getMultiOpts("colKey");
        for (String tableKey : keyList) {
            String[] tableParts = tableKey.split("\\.");
            String tableName = tableParts[0];
            String colName = tableParts[1];
            keyFieldByTable.put(tableName, colName);
        }

        // == Run export if flag is given
        String[] tableNames = opts.getOpt("dbTables", "").split(",");
        File exportDirObj = new File(exportDir);
        if (export) {
            // Prepare export dir if needed
            if (!exportDirObj.exists()) {
                LOG.info("Creating cache dir {}", exportDirObj);
                exportDirObj.mkdirs();
            }

            for (String tablePattern : tableNames) {
                LOG.info("Inspecting DB tablePattern={}", tablePattern);
                List<DbTable> dbTableList = JdbcUtils.getDbTable(tablePattern, dbUrl, dbUser, dbPass);
                dbTables.addAll(dbTableList);
            }

            // Exporting JSON files
            for (DbTable dbTable : dbTables) {
                File cacheFile = new File(exportDir + "/" + dbTable.getTableName() + EXPORT_FILE_EXT);
                if (codeGen.isOverrideFiles() || !cacheFile.exists()) {
                    LOG.info("Exporting DbTable {}", cacheFile);
                    codeGen.getObjectMapper().writeValue(cacheFile, dbTable);
                }
            }
        } else {
            // Prepare dbTables - read in epxorted file if found.
            if (dbTablesFromExport && exportDirObj.exists()) {
                File[] cacheFiles = exportDirObj.listFiles();
                if (cacheFiles != null) {
                    for (File cacheFile : cacheFiles) {
                        if (cacheFile.isFile() && cacheFile.getName().endsWith(EXPORT_FILE_EXT)) {
                            LOG.debug("Reading DbTable {}", cacheFile);
                            DbTable dbTable = codeGen.getObjectMapper().readValue(cacheFile, DbTable.class);
                            dbTables.add(dbTable);
                        }
                    }
                }
            }

            // == Query for DB table list if needed
            for (String tablePattern : tableNames) {
                if (dbTables.stream().filter(e -> e.getTableName().equals(tablePattern)).findAny().isPresent()) {
                    continue;
                } else {
                    LOG.info("Inspecting DB tablePattern={}", tablePattern);
                    List<DbTable> dbTableList = JdbcUtils.getDbTable(tablePattern, dbUrl, dbUser, dbPass);
                    dbTables.addAll(dbTableList);
                }
            }
            LOG.debug("Found {} dbTables for code generation.", dbTables.size());
        }
    }

    @Override
    public void afterTemplateSet() throws Exception {
        if (export) {
            // Do not generate classes if export mode is on.
            return;
        }

        LOG.info("Generating DB to Java classes.");
        CmdOpts opts = codeGen.getOpts();
        boolean useSingularName = opts.getBooleanOpt("useSingularDomain", true);
        String apiUrlMapping = opts.getOpt("apiUrlMapping", "/api");
        String controllerType = opts.getOpt("controllerType", null);
        String templatePath = codeGen.getTemplatesDir() + "/" + dbTableTemplateDir;

        if (controllerType == null) {
            if (codeGen.getTemplateSetName().indexOf("api") >= 0) {
                controllerType = "API";
            } else {
                controllerType = "UI";
            }
        }

        Map<String, Object> templateModel = new HashMap<>(codeGen.getTemplateModel());
        String basePackage = (String) templateModel.get("basePackage");

        templateModel.put("apiUrlMapping", apiUrlMapping);
        templateModel.put("controllerType", controllerType);

        for (DbTable dbTable : dbTables) {
            // Override "dbTable" for each iteration to generate new domain
            templateModel.put("dbTable", dbTable);

            // Each generate method will prepare and add models into template model map.
            // Note that the order of these generated classes are important since model inserted
            // into template model has dependencies on each other.
            generateDomain(templateModel, dbTable, useSingularName);
            generateDAO(templateModel);
            generateService(templateModel);

            if (controllerType.indexOf("UI") >= 0) {
                generateWebController(templateModel);
                generateWebFtlViews(templateModel);
            } else {
                Domain domain = (Domain) templateModel.get("domain");
                templateModel.put("apiUrlMapping", apiUrlMapping);

                codeGen.writeJavaOutputFile(basePackage + ".web.controller.api", "/src/main/java",
                        "Api" + domain.getClassName() + "Controller.java",
                        templatePath + "/src/main/java/web/controller/api/ApiDomainController.java", templateModel);
            }
        }

        String basePackagePath = (String) templateModel.get("basePackagePath");
        generateIfNotExists("src/main/java/" + basePackagePath + "/AppException.java",
                templatePath + "/src/main/java/AppException.java", templateModel);
        generateIfNotExists("src/main/java/" + basePackagePath + "/web/controller/AbstractController.java",
                templatePath + "/src/main/java/web/controller/AbstractController.java", templateModel);
        generateIfNotExists("src/main/java/" + basePackagePath + "/data/dao/AbstractDAO.java",
                templatePath + "/src/main/java/data/dao/AbstractDAO.java", templateModel);
        generateIfNotExists("src/main/java/" + basePackagePath + "/data/dao/Paging.java",
                templatePath + "/src/main/java/data/dao/Paging.java", templateModel);
        generateIfNotExists("src/main/java/" + basePackagePath + "/data/dao/PagingList.java",
                templatePath + "/src/main/java/data/dao/PagingList.java", templateModel);
    }

    private void generateIfNotExists(String outputFileName, String templatePath, Map<String, Object> templateModel) throws Exception {
        File outputFile = new File(codeGen.getOutputDir(), outputFileName);
        if (!outputFile.exists()) {
            codeGen.writeOutputFile(outputFileName,
                    templatePath,
                    templateModel);
        }
    }

    private void generateDomain(Map<String, Object> templateModel, DbTable dbTable, boolean useSingularName) throws Exception {
        String basePackage = (String) templateModel.get("basePackage");
        String className = tableToDomainNames.get(dbTable.getTableName());
        if (className == null) {
            className = JdbcUtils.tableToClass(dbTable.getTableName(), useSingularName);
        }
        Domain domain = new Domain(basePackage + ".data.domain", className);
        populateDomain(domain, className, dbTable);
        templateModel.put("domain", domain);

        codeGen.writeJavaOutputFile(domain.getPackageName(), "/src/main/java", domain.getClassName() + ".java",
                codeGen.getTemplatesDir() + "/" + dbTableTemplateDir + "/src/main/java/data/domain/Domain.java", templateModel);
    }

    private void populateDomain(Domain domain, String className, DbTable dbTable) {
        for (DbField dbField : dbTable.getFields()) {
            String colName = dbField.getColumnName();

            // Auto dropping is_ prefix to avoid boolean getter problems.
            if (colName.startsWith("is_")) {
                colName = colName.substring(3);
            }

            String camelName = JavaUtils.underscoreToCamel(colName);
            String getter = "get" + JavaUtils.upCase(camelName);
            String setter = "set" + JavaUtils.upCase(camelName);
            String javaType = codeGen.getDbDataTypeToJavaTypes().get(dbField.getDataType());
            if (javaType == null) {
                javaType = JdbcUtils.sqlTypeToJava(dbField.getDataType());
            }

            if (javaType.equals("boolean")) {
                getter = "is" + JavaUtils.upCase(camelName);
            }

            Field field = new Field(
                    javaType,
                    camelName,
                    getter,
                    setter
            );

            Map<String, List<String>> enumColumns = enumValuesByTable.get(dbTable.getTableName());
            if (enumColumns != null) {
                List<String> enumValues = enumColumns.get(dbField.getColumnName());
                if (enumValues != null) {
                    field.setEnumValues(enumValues);
                    field.setType(className + "." + JavaUtils.upCase(field.getName()));
                }
            }

            field.setDbField(dbField);
            domain.getFields().add(field);
        }

        // Override key if found
        String key = keyFieldByTable.get(dbTable.getTableName());
        if (key != null) {
            Optional<Field> keyField = domain.getFields().stream().
                    filter(e -> key.equals(e.getDbField().getColumnName())).findFirst();
            if (!keyField.isPresent()) {
                throw new AppException("Explicit key column " + key + " not found for table: " + dbTable);
            }

            domain.getFields().forEach(e -> e.getDbField().setKey(false));
            keyField.get().getDbField().setKey(true);
        }

        // Ensure we have at least one key!
        Optional<Field> keyField = domain.getFields().stream().filter(e -> e.getDbField().isKey()).findFirst();
        if (!keyField.isPresent()) {
            throw new AppException("No key field found on table: " + dbTable.getTableName());
        }
    }

    private void generateDAO(Map<String, Object> templateModel) throws Exception {
        String basePackage = (String) templateModel.get("basePackage");
        Domain domain = (Domain) templateModel.get("domain");
        DAO dao = new DAO(basePackage + ".data.dao", domain);
        templateModel.put("dao", dao);

        codeGen.writeJavaOutputFile(dao.getPackageName(), "/src/main/java", dao.getClassName() + ".java",
                codeGen.getTemplatesDir() + "/" + dbTableTemplateDir + "/src/main/java/data/dao/DomainDAO.java", templateModel);
    }

    private void generateService(Map<String, Object> templateModel) throws Exception {
        String basePackage = (String) templateModel.get("basePackage");
        DAO dao = (DAO) templateModel.get("dao");
        Service service = new Service(basePackage + ".service", dao);
        templateModel.put("service", service);

        codeGen.writeJavaOutputFile(service.getPackageName(), "/src/main/java", service.getClassName() + ".java",
                codeGen.getTemplatesDir() + "/" + dbTableTemplateDir + "/src/main/java/service/DomainService.java", templateModel);
    }

    private void generateWebController(Map<String, Object> templateModel) throws Exception {
        CmdOpts opts = codeGen.getOpts();
        String webUiUrlMapping = opts.getOpt("webUiUrlMapping", "/admin");
        templateModel.put("webUiUrlMapping", webUiUrlMapping);

        String basePackage = (String) templateModel.get("basePackage");
        Service service = (Service) templateModel.get("service");
        Controller controller = new Controller(basePackage + ".web.controller.admin", service, webUiUrlMapping);
        templateModel.put("controller", controller);

        codeGen.writeJavaOutputFile(controller.getPackageName(), "/src/main/java", controller.getClassName() + ".java",
                codeGen.getTemplatesDir() + "/" + dbTableTemplateDir + "/src/main/java/web/controller/DomainController.java", templateModel);
    }

    private void generateWebFtlViews(Map<String, Object> templateModel) throws Exception {
        CmdOpts opts = codeGen.getOpts();
        String templatePath = codeGen.getTemplatesDir() + "/" + dbTableTemplateDir + "/src/main/webapp/WEB-INF/ftl/admin";
        String webUiUrlMapping = opts.getOpt("webUiUrlMapping", "/admin");
        templateModel.put("webUiUrlMapping", webUiUrlMapping);

        Domain domain = (Domain) templateModel.get("domain");
        String outputFilePath = "src/main/webapp/WEB-INF/ftl" + webUiUrlMapping + "/" + domain.getClassNameUrl();
        writeFtlViewFile(outputFilePath, templatePath, "/create.ftl", templateModel);
        writeFtlViewFile(outputFilePath, templatePath, "/delete.ftl", templateModel);
        writeFtlViewFile(outputFilePath, templatePath, "/detail.ftl", templateModel);
        writeFtlViewFile(outputFilePath, templatePath, "/edit.ftl", templateModel);
        writeFtlViewFile(outputFilePath, templatePath, "/form-body.ftl", templateModel);
        writeFtlViewFile(outputFilePath, templatePath, "/list.ftl", templateModel);
    }

    private void writeFtlViewFile(String outputFileName, String templatePath, String templateName,
                                  Map<String, Object> templateModel) throws Exception {
        codeGen.writeOutputFile(outputFileName + templateName,
                templatePath + templateName, templateModel);
    }
}
