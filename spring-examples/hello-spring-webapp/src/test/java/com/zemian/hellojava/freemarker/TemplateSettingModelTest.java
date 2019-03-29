package com.zemian.hellojava.freemarker;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static com.zemian.hellojava.support.JavaUtils.map;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(classes = FtlConfig.class)
public class TemplateSettingModelTest extends SpringTestBase {
    @Autowired
    FtlService ftlService;

    @Test
    @Ignore
    public void test() throws Exception {
        // This will not work!
        String ret = ftlService.eval("${s?api.getName()}", map("s", new TemplateSettingModel()));
        System.out.println(ret);
    }
}