package com.zemian.hellojava.freemarker;

import com.zemian.hellojava.data.domain.Setting;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Test;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

public class HelloFreemarkerTest {

    @Test
    public void hello() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File("src/test/resources/freemarker"));

        Map<String, Object> root = new HashMap<>();

        Template template = cfg.getTemplate("HelloFreemarkerTest.ftl");
        StringWriter out = new StringWriter();
        template.process(root, out);
        assertThat(out.toString(), is("Hello"));

    }

    /*
    This is based on https://freemarker.apache.org/docs/pgui_quickstart.html
     */
    @Test
    public void quickstart() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File("src/test/resources/freemarker"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        Map<String, Object> root = new HashMap<>();
        root.put("user", "Big Joe");

        Setting setting = new Setting("TEST", "foo", "bar");
        root.put("foo", setting);
        root.put("TEST.foo", setting);

        Template template = cfg.getTemplate("HelloFreemarkerTest_quickstart.ftl");

        Writer out = new OutputStreamWriter(System.out);
        template.process(root, out);
    }

    @Test
    public void test() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File("src/test/resources/freemarker"));

        Map<String, Object> root = new HashMap<>();
        root.put("user", "Big Joe");
        root.put("someString", "Some value");
        root.put("otherString", "JSON\nsyntax");
        root.put("someNumber", 3.14);
        root.put("someNumber2", 33456.9876);
        root.put("someInteger", 99);
        root.put("someInteger2", 33456);
        root.put("someBoolean", true);
        root.put("someDate", new SimpleDateFormat("yyyy-MM-dd").parse("2014-02-28"));
        root.put("someTime", new SimpleDateFormat("HH:mm:ss.SSSXXX").parse("20:50:30.5+02:00"));
        root.put("someDatetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSXXX").parse("2014-02-28 20:50:30.5+02:00"));
        root.put("someList", Arrays.asList("JSON", "syntax", 1, 2, 3));

        Map<String, Object> map = new HashMap<>();
        map.put("JSON syntax", true);
        map.put("nestedList", Arrays.asList(1, 2, 3));
        root.put("someMap", map);

        root.put("someXML", "<example x=\"1\">text</example>");

        Template template = cfg.getTemplate("DataTypesDisplayTest.ftl");
        StringWriter out = new StringWriter();
        template.process(root, out);

        assertThat(out.toString(), not(isEmptyString()));
        System.out.println(out.toString());
    }
}
