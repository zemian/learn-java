package com.zemian.toolbox.codegen.plugins;

import com.zemian.toolbox.support.CmdOpts;
import com.zemian.toolbox.support.JavaUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JavaProjectPlugin extends AbstractPlugin {
    private static final Logger LOG = LoggerFactory.getLogger(JavaProjectPlugin.class);

    @Override
    public String getHelp() {
        return "Generate a Java project structure using the following options as data model for \n" +
                "template. We will use <projectName> as maven artifactId.\n" +
                "  --groupId=NAME\n" +
                "      Project group name to generate (groupId). Default to <templateSetName>.\n" +
                "  --basePackage=NAME\n" +
                "      Java base package name to use for classes generation.\n" +
                "      Default <groupId> + '.' + <projectName> (we auto convert to proper Java package name).\n" +
                "";
    }

    @Override
    public void init() throws Exception {
        // Populate project related template models
        CmdOpts opts = codeGen.getOpts();
        String groupId = opts.getOpt("groupId", codeGen.getTemplateSetName());
        String projectName = codeGen.getProjectName(); // artifactId

        // We will collapse any "dash" separators before converting to packageName.
        String defBasePackage = JavaUtils.urlNameToPackage(projectName.replaceAll("\\-", ""));
        if (StringUtils.isNotEmpty(groupId) && !groupId.equals(codeGen.getTemplateSetName())) {
            defBasePackage = JavaUtils.urlNameToPackage(groupId.replaceAll("\\-", "")) + "." + defBasePackage;
        }
        String basePackage = opts.getOpt("basePackage", defBasePackage);
        String basePackagePath = basePackage.replaceAll("\\.", "/");

        Map<String, Object> templateModel = codeGen.getTemplateModel();
        templateModel.put("groupId", groupId);
        templateModel.put("artifactId", projectName);
        templateModel.put("basePackage", basePackage);
        templateModel.put("basePackagePath", basePackagePath);

        LOG.info("Initialized projectName={} with basePackage={}", projectName, basePackage);
    }
}
