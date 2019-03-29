package com.zemian.hellojava.data;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

@ContextConfiguration(classes = CommonDataConfig.class)
public class DBTypesTest extends SpringTestBase {
    @Autowired
    private JdbcTemplate jdbc;

    @Test
    public void dbTypes() {
        jdbc.execute((ConnectionCallback<Object>) conn -> {
            Map<String, Class<?>> typeMap = conn.getTypeMap();
            if (typeMap != null) {
                for (Map.Entry<String, Class<?>> entry : typeMap.entrySet()) {
                    System.out.println(entry);
                }
            }
            return null;
        });
    }
}