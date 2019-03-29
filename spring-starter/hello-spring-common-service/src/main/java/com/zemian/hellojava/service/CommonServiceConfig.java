package com.zemian.hellojava.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zemian.hellojava.data.CommonDataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@Import(CommonDataConfig.class)
@EnableTransactionManagement
public class CommonServiceConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        DataSourceTransactionManager bean = new DataSourceTransactionManager();
        bean.setDataSource(dataSource);
        return bean;
    }

    // == Components needed by JSON serialization services
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper bean = new ObjectMapper();
        bean.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        bean.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        bean.registerModule(new JavaTimeModule());
        return bean;
    }

    // == Business Services
    @Bean
    UserService userService() {
        return new UserService();
    }

    @Bean
    SettingService settingService() {
        return new SettingService();
    }

    @Bean
    AuditLogService auditLogService() {
        return new AuditLogService();
    }
}
