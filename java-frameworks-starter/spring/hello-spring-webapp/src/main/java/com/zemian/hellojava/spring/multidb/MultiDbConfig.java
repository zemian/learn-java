package com.zemian.hellojava.spring.multidb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Profile("demo-spring-multidb")
@Configuration
@EnableTransactionManagement
public class MultiDbConfig {
    // == First set of datasource
    @Bean("dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource bean = new DriverManagerDataSource();
        bean.setDriverClassName(env.getProperty("app.ds.driverClassName"));
        bean.setUrl(env.getProperty("app.ds.url"));
        bean.setUsername(env.getProperty("app.ds.username"));
        bean.setPassword("");

        return bean;
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate bean = new JdbcTemplate(dataSource());
        return bean;
    }


    @Bean("platformTransactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        DataSourceTransactionManager bean = new DataSourceTransactionManager();
        bean.setDataSource(dataSource());
        return bean;
    }

    // == Second set of datasource
    @Autowired
    Environment env;

    @Bean("dataSourceDB2")
    public DataSource dataSourceDB2() {
        String driverClassName = env.getProperty("app.ds.driverClassName");
        DriverManagerDataSource bean = new DriverManagerDataSource();
        bean.setDriverClassName(driverClassName);
        bean.setUrl("jdbc:postgresql://localhost/postgres");
        bean.setUsername("postgres");
        bean.setPassword("");

        return bean;
    }

    @Bean("jdbcTemplateDB2")
    public JdbcTemplate jdbcTemplateDB2() {
        JdbcTemplate bean = new JdbcTemplate(dataSourceDB2());
        return bean;
    }

    @Bean("platformTransactionManagerDB2")
    public PlatformTransactionManager platformTransactionManagerDB2() {
        DataSourceTransactionManager bean = new DataSourceTransactionManager();
        bean.setDataSource(dataSourceDB2());
        return bean;
    }

    // == Services
    @Bean
    MultiTxService multiTxService() {
        return new MultiTxService();
    }
}
