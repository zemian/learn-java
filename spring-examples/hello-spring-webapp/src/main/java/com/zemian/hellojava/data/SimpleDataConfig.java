package com.zemian.hellojava.data;

import com.zemian.hellojava.CommonConfig;
import com.zemian.hellojava.cipher.Crypto;
import com.zemian.hellojava.cipher.CommonCryptoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Import({CommonConfig.class, CommonCryptoConfig.class})
public class SimpleDataConfig {

    @Autowired
    private Environment env;

    @Autowired
    private Crypto crypto;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource bean = new DriverManagerDataSource();
        String password = env.getProperty("app.ds.password");
        boolean disablePasswordDecryption =
                Boolean.parseBoolean(env.getProperty("app.ds.disablePasswordDecryption", "false"));
        if (!disablePasswordDecryption) {
            password = crypto.decrypt(password);
        }

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
}
