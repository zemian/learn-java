package com.zemian.hellojava.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemian.hellojava.freemarker.JavaToStringMethodModel;
import com.zemian.hellojava.spring.tx.TxServiceWebConfig;
import com.zemian.hellojava.web.listener.UserSessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Web MVC Settings
 */
@Configuration
@ComponentScan
@EnableWebMvc
@Import(TxServiceWebConfig.class)
public class AppWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/error").setViewName("error");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(freeMarkerViewResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Protect all admin URLs
        registry.addInterceptor(userLoginInterceptor()).addPathPatterns("/admin/**");
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
        Map<String, Object> sharedVars = new HashMap<>();
        // Register any global shared variable here
        sharedVars.put("javaToString", new JavaToStringMethodModel());

        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("/WEB-INF/ftl/");
        bean.setConfigLocation(new ClassPathResource("/hellojava/freemarker.properties"));
        bean.setFreemarkerVariables(sharedVars);
        return bean;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setConfiguration(freeMarkerConfigurationFactoryBean().getObject());
        return configurer;
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver bean = new FreeMarkerViewResolver();
        bean.setSuffix(".ftl");
        bean.setExposeSpringMacroHelpers(true);
        bean.setExposeRequestAttributes(true);
        return bean;
    }

    @Bean
    public UserSessionInterceptor userLoginInterceptor() {
        UserSessionInterceptor bean =  new UserSessionInterceptor();
        bean.setLoginUrl("/login");
        return bean;
    }

    // == API Config

    // This jackson object mapper is configured in service module layer. We will reuse it here.
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter bean = new MappingJackson2HttpMessageConverter(objectMapper);
        return bean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }
}
