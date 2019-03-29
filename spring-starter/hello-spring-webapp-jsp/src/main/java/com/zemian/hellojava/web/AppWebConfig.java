package com.zemian.hellojava.web;

import com.zemian.hellojava.web.listener.UserSessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC Settings
 */
@Configuration
@ComponentScan
@EnableWebMvc
public class AppWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/error").setViewName("error");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
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
    public UserSessionInterceptor userLoginInterceptor() {
        UserSessionInterceptor bean =  new UserSessionInterceptor();
        bean.setLoginUrl("/login");
        return bean;
    }
}
