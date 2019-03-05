package ${basePackage}.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class Hello {
    public void run(String[] args) {
        System.out.println("Hello World");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext spring = new AnnotationConfigApplicationContext(Config.class);
        spring.getBean(Hello.class).run(args);
        spring.close();
    }

    @Configuration
    public static class Config {
        @Bean public Hello hello() {
            return new Hello();
        }
    }
}
