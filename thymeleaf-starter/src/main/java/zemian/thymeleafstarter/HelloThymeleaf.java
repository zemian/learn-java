package zemian.thymeleafstarter;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

public class HelloThymeleaf {
    public static void main(String[] args) {

        // Default anyway
        //StringTemplateResolver r = new StringTemplateResolver();
        //t.setTemplateResolver(r);

        TemplateEngine te = new TemplateEngine();
        //System.out.println(te);

        Context data = new Context();
        data.setVariable("message", "Hello World");

        String result = te.process("<p th:text=\"${message}\">Hello Me</p>", data);
        System.out.println(result);

        data.setVariable("name", "Zemian");
        result = te.process("<p th:text=\"'Hello ' + ${name}\">Hello Me</p>", data);
        System.out.println(result);

        result = te.process("<p th:text=\"'Hello ${name} again -- oops!'\">Hello Me</p>", data);
        System.out.println(result);

        result = te.process("Inline process: [[${message}]]", data);
        System.out.println(result);
    }
}