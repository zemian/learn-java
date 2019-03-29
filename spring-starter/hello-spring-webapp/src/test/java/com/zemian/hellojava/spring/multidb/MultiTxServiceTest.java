package com.zemian.hellojava.spring.multidb;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("demo-spring-multidb")
@ContextConfiguration(classes = MultiDbConfig.class)
public class MultiTxServiceTest extends SpringTestBase {
    @Autowired
    MultiTxService multiTxService;

    @Test
    public void testMultiTxService() {
        multiTxService.create();
    }
}