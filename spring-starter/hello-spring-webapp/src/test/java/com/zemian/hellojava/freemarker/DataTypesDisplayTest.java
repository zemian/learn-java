package com.zemian.hellojava.freemarker;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static com.zemian.hellojava.support.JavaUtils.map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = FtlConfig.class)
public class DataTypesDisplayTest extends SpringTestBase {
    @Autowired
    FtlService ftlService;

    @Test
    public void dates() throws Exception {
        //You may not render date directly!
        //assertThat(ftlService.eval("${dt}", map("dt", new Date())), is("Dec 4, 2017"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt = df.parse("2017-12-4 20:23");

        assertThat(ftlService.eval("${dt?date}", map("dt", dt)), is("Dec 4, 2017"));
        assertThat(ftlService.eval("${dt?time}", map("dt", dt)), is("8:23:00 PM"));
        assertThat(ftlService.eval("${dt?datetime}", map("dt", dt)), is("Dec 4, 2017 8:23:00 PM"));
    }

    @Test
    public void java8dates() throws Exception {
        // We need freemarker-java8 dep to support these dates

        LocalDateTime dt = LocalDateTime.parse("2017-12-04T09:43:00");
        assertThat(ftlService.eval("${dt}", map("dt", dt)), is("2017-12-04T09:43"));
        //assertThat(ftlService.eval("${dt.format()}", map("dt", dt)), is("2017-12-04T09:43"));
        assertThat(ftlService.eval("${dt.format('HH:mm:ss')}", map("dt", dt)), is("09:43:00"));
        assertThat(ftlService.eval("${dt.format('yyyy-MM-dd HH:mm:ss')}", map("dt", dt)), is("2017-12-04 09:43:00"));
    }
}
