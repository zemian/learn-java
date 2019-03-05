package com.zemian.hellojava.data;

import com.zemian.hellojava.CommonConfig;
import com.zemian.hellojava.cipher.Crypto;
import com.zemian.hellojava.cipher.CommonCryptoConfig;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@Import({CommonConfig.class, CommonCryptoConfig.class})
public class DBPoolDataConfig {

    @Autowired
    private Environment env;

    @Autowired
    private Crypto crypto;

    @Bean
    public DataSource dataSource() throws Exception {
        // Setup poolProps
        String key = "app.ds.pool.propsResource";
        String resName = env.getProperty(key);
        ResourcePropertySource poolPropsSource = new ResourcePropertySource(key, resName);
        Properties poolProps = new Properties();
        poolProps.putAll(poolPropsSource.getSource());

        // Override these few main props of driver, url, username and password values from
        // the app.properties directly into the poolProps
        String password = env.getProperty("app.ds.password");
        boolean disablePasswordDecryption =
                Boolean.parseBoolean(env.getProperty("app.ds.disablePasswordDecryption", "false"));
        if (!disablePasswordDecryption) {
            password = crypto.decrypt(password);
        }

        poolProps.put("driverClassName", env.getProperty("app.ds.driverClassName"));
        poolProps.put("url", env.getProperty("app.ds.url"));
        poolProps.put("username", env.getProperty("app.ds.username"));
        poolProps.put("password", password);

        PoolConfiguration poolConfig = DataSourceFactory.parsePoolProperties(poolProps);

        // Setup additional JDBC Driver Connection properties if there are any
        key = "app.ds.driver.propsResource";
        resName = env.getProperty(key);
        ResourcePropertySource dbPropsSource = new ResourcePropertySource(key, resName);
        Properties dbProps = new Properties();
        dbProps.putAll(dbPropsSource.getSource());

        poolConfig.setDbProperties(dbProps);

        // Now create the actual DataSource from poolConfig
        org.apache.tomcat.jdbc.pool.DataSource bean =
                new org.apache.tomcat.jdbc.pool.DataSource(poolConfig);

        // Initialize the pool now
        bean.createPool();

        return bean;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception {
        JdbcTemplate bean = new JdbcTemplate(dataSource());
        return bean;
    }

    @Autowired
    @Bean
    public MBeanExporter mBeanExporter(DataSource dataSource, Environment env) {
        String appName = env.getProperty("app.name");
        InterfaceBasedMBeanInfoAssembler assembler = new InterfaceBasedMBeanInfoAssembler();
        assembler.setManagedInterfaces(new Class[]{ConnectionPoolMBean.class});

        HashMap<String, Object> beans = new HashMap<>();
        beans.put("tomcat-jdbc-pool:name=" + appName + "DataSource", dataSource);

        MBeanExporter a = new MBeanExporter();
        a.setBeans(beans);
        a.setAssembler(assembler);
        return a;
    }
}
