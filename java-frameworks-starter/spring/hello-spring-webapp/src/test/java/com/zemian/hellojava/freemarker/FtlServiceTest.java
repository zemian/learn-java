package com.zemian.hellojava.freemarker;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static com.zemian.hellojava.support.JavaUtils.map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = FtlConfig.class)
public class FtlServiceTest extends SpringTestBase {
    @Autowired
    FtlService ftlService;

    @Test
    public void numberFormat() {
        String ret = ftlService.eval("${n}", map("n", 1_234));
        assertThat(ret, is("1234"));
    }
}
