package com.zemian.hellojava.spring.env;

import com.zemian.hellojava.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public class HelloEnv {

    @Configuration
    public static class Config {
        @Autowired
        private Environment env;

        @Bean
        public DataGenerator dataGenerator() {
            DataGenerator bean = new DataGenerator();
            bean.setName(env.getProperty("foo"));
            return bean;
        }
    }

    public static void main(String[] args) {
        System.out.println("==");
        main1(args);
        System.out.println("==");
        main2(args);
    }

    public static void main1(String[] args) {
        AnnotationConfigApplicationContext spring = new AnnotationConfigApplicationContext(Config.class);
        System.out.println("Spring=" + spring);
        ConfigurableEnvironment env = spring.getEnvironment();
        System.out.println("Env=" + env);

        DataGenerator bean = spring.getBean(DataGenerator.class);
        System.out.println("DataGenerator=" + bean);
        System.out.println("DataGenerator.createHello=" + bean.createHello("World"));
    }


    public static void main2(String[] args) {
        //NOTES you can setup env and config class with empty spring app ctx first,
        // then later "refresh" it.
        AnnotationConfigApplicationContext spring = new AnnotationConfigApplicationContext();
        System.out.println("Spring=" + spring);
        ConfigurableEnvironment env = spring.getEnvironment();
        System.out.println("Env=" + env);

        Properties props = new Properties();
        props.setProperty("foo", "bar");
        PropertiesPropertySource propsSource = new PropertiesPropertySource("myPropertiesPropertySource", props);
        env.getPropertySources().addFirst(propsSource);

        spring.register(Config.class);
        spring.refresh();
        System.out.println("After referesh: Spring=" + spring);env = spring.getEnvironment();
        System.out.println("After referesh: Env=" + env);

        DataGenerator bean = spring.getBean(DataGenerator.class);
        System.out.println("DataGenerator=" + bean);
        System.out.println("DataGenerator.createHello=" + bean.createHello("World"));
    }
}
