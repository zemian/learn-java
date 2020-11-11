package ${basePackage}.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * Web MVC/API Settings
 */
@Configuration
@ComponentScan("${basePackage}")
@EnableWebMvc
<#if dbUrl??>@EnableTransactionManagement</#if>
public class AppWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Protect all admin URLs
        //registry.addInterceptor(userLoginInterceptor()).addPathPatterns("/api/**");
    }

    // == DataAccess Config
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource bean = new DriverManagerDataSource();
        bean.setDriverClassName("${dbDriver!'org.postgresql.Driver'}");
        bean.setUrl("${dbUrl!'jdbc:postgresql://localhost/test'}");
        bean.setUsername("${dbUser!'postgres'}");
        bean.setPassword("${dbPass!}");
        return bean;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate bean = new JdbcTemplate(dataSource());
        return bean;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        DataSourceTransactionManager bean = new DataSourceTransactionManager();
        bean.setDataSource(dataSource());
        return bean;
    }

    // == API Config

    // This jackson object mapper is configured in service module layer. We will reuse it here.
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper bean = new ObjectMapper();
        bean.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        bean.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        bean.registerModule(new JavaTimeModule());
        return bean;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter bean = new MappingJackson2HttpMessageConverter(objectMapper());
        return bean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }
}
