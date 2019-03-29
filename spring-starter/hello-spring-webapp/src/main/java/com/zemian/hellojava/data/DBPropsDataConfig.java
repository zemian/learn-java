package com.zemian.hellojava.data;

import com.zemian.hellojava.CommonConfig;
import com.zemian.hellojava.cipher.Crypto;
import com.zemian.hellojava.cipher.CommonCryptoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import({CommonConfig.class, CommonCryptoConfig.class})
public class DBPropsDataConfig {

    @Autowired
    private Environment env;

    @Autowired
    private Crypto crypto;

    @Bean
    public DataSource dataSource() throws Exception {
        // Setup ds
        DriverManagerDataSource bean = new DriverManagerDataSource();

        // Setup JDBC Driver Connection properties
        String key = "app.ds.driver.propsResource";
        String resName = env.getProperty(key);
        ResourcePropertySource dbPropsSource = new ResourcePropertySource(key, resName);
        Properties dbProps = new Properties();
        dbProps.putAll(dbPropsSource.getSource());
        bean.setConnectionProperties(dbProps);

        // Lastly override the main props of driver, url, username and password values from
        // the app.properties directly.
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
    public JdbcTemplate jdbcTemplate() throws Exception {
        JdbcTemplate bean = new JdbcTemplate(dataSource());
        return bean;
    }
}
