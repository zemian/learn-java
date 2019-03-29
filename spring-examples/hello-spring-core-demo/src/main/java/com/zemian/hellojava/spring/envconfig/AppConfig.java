package com.zemian.hellojava.spring.envconfig;

import com.zemian.hellojava.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/hellojava/app.properties")
@Import({AppConfigProd.class, AppConfigQa.class})
public class AppConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataGenerator dataGenerator() {
        DataGenerator bean = new DataGenerator();
        bean.setName(env.getProperty("app.env"));
        return bean;
    }
}
