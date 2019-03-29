package com.zemian.hellojava.freemarker;

import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class FtlJava8DatesConfig {
    @Bean
    public Configuration configuration() {
        Configuration bean = new Configuration(Configuration.VERSION_2_3_27);
        bean.setNumberFormat("computer");
        bean.setAPIBuiltinEnabled(true);
        bean.setObjectWrapper(new Java8DatesWrapper(bean.getIncompatibleImprovements()));
        return bean;
    }

    @Bean
    public FtlService ftlService() {
        return new FtlService();
    }
}
