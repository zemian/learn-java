package com.zemian.hellojava.data.support;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@ContextConfiguration(classes = DirectConnConfig.class)
public class DirectConnServiceTest extends SpringTestBase {
    @Autowired
    private DirectConnService connService;

    @Test
    public void execute() {
        connService.execute(conn -> {
            assertThat(conn, notNullValue());
            return null;
        });
    }
}