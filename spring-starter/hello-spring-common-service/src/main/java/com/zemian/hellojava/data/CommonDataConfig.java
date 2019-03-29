package com.zemian.hellojava.data;

import com.zemian.hellojava.CommonConfig;
import com.zemian.hellojava.cipher.CommonCryptoConfig;
import com.zemian.hellojava.cipher.Crypto;
import com.zemian.hellojava.data.dao.AuditLogDAO;
import com.zemian.hellojava.data.dao.SettingDAO;
import com.zemian.hellojava.data.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Data layer configuration.
 *
 * @Author Zemian Deng 2017
 */
@Configuration
@Import({CommonConfig.class, CommonCryptoConfig.class})
public class CommonDataConfig {

    @Autowired
    private Environment env;

    @Autowired
    private Crypto crypto;

    @Bean
    public DataSource dataSource() {
        String password = crypto.decrypt(env.getProperty("app.ds.password"));
        DriverManagerDataSource bean = new DriverManagerDataSource();
        bean.setDriverClassName(env.getProperty("app.ds.driverClassName"));
        bean.setUrl(env.getProperty("app.ds.url"));
        bean.setUsername(env.getProperty("app.ds.username"));
        bean.setPassword(password);

        return bean;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate bean = new JdbcTemplate(dataSource());
        return bean;
    }

    @Bean
    SettingDAO settingDAO() {
        return new SettingDAO();
    }

    @Bean
    UserDAO userDAO() {
        return new UserDAO();
    }

    @Bean
    AuditLogDAO auditLogDAO() {
        return new AuditLogDAO();
    }
}
