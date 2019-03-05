package com.zemian.hellojava.freemarker;

import com.zemian.hellojava.AppException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.StringReader;
import java.io.StringWriter;

public class FtlService {
    @Autowired
    private Configuration config;

    public String eval(String templateVal, Object dataModel) {
        try {
            StringWriter output = new StringWriter();
            Template template = new Template("eval", new StringReader(templateVal), config);
            template.process(dataModel, output);
            return output.toString();
        } catch (Exception e) {
            throw new AppException("Failed to eval FTL template.", e);
        }
    }

    public String process(String templateName, Object dataModel) {
        try {
            StringWriter output = new StringWriter();
            Template template = config.getTemplate(templateName);
            template.process(dataModel, output);
            return output.toString();
        } catch (Exception e) {
            throw new AppException("Failed to process FTL template name " + templateName, e);
        }
    }
}
