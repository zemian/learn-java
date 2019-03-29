package com.zemian.perfrunner.camel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootHelloTest {
    @Autowired
    private DataSource dataSource;

    @Value("${app.env}")
    String appEnv;

    @Test
    public void test() {
        assertThat(appEnv, is("TEST"));
        assertThat(dataSource, notNullValue());
    }
}
