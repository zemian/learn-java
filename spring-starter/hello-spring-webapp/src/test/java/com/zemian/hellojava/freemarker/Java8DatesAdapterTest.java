package com.zemian.hellojava.freemarker;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;

import static com.zemian.hellojava.support.JavaUtils.map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = FtlJava8DatesConfig.class)
public class Java8DatesAdapterTest extends SpringTestBase {
    @Autowired
    FtlService ftlService;

    @Test
    public void test() throws Exception {
        LocalDateTime dt = LocalDateTime.parse("2017-12-04T09:43:00");
        String ret = ftlService.eval("${dt}", map("dt", dt));
        assertThat(ret, is("2017-12-04T09:43:00"));
    }
}