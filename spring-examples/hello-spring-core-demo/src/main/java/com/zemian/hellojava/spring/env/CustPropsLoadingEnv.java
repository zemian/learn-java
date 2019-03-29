package com.zemian.hellojava.spring.env;

import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Properties;

public class CustPropsLoadingEnv {
    public static void main(String[] args) throws Exception {
        System.out.println("== main3");
        main3(args);

        System.out.println("== main4");
        main4(args);
    }

    public static void main3(String[] args) throws Exception {
        // Reading Properties using Spring supported classed.
        ResourcePropertySource propsSource = new ResourcePropertySource(
                "propsSource","classpath:/com/zemian/hellojava/spring/env/mygroup.properties");

        for (String name : propsSource.getPropertyNames()) {
            System.out.println("Property " + name + " " + propsSource.getProperty(name));
        }

        System.out.println("propsSource.getSource().getClass()=" + propsSource.getSource().getClass());

        // How to cast back into a Properties object
        Properties props = new Properties();
        props.putAll(propsSource.getSource());
        System.out.println("props=" + props);
    }

    public static void main4(String[] args) throws Exception {
        // Reading Properties using Spring supported classed and perform placeholder replacement
        ResourcePropertySource propsSource = new ResourcePropertySource(
                "propsSource","classpath:/com/zemian/hellojava/spring/env/mygroup.properties");

        // How to cast back into a Properties object
        Properties props = new Properties();
        props.putAll(propsSource.getSource());

        // Example1: Explicitly parse single string!
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
        String myStrFoo = helper.replacePlaceholders("myStrFoo ${foo}", props);
        System.out.println("Custom placeholder replace myStrFoo=" + myStrFoo);

        // NOTE that PropertySourcesPlaceholderConfigurer and PropertySourcesPlaceholderConfigurer are
        // designed to resolve variables from a BeanFactory objects (meaning variables found in either xmlconfig
        // or @Value in javaconfig bean definitions. They usually will create a @Bean in their setup to do this.
    }
}
