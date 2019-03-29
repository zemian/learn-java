package zemian.myjava.hello;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class Hello {
    public static void main(String ... args) throws Exception {
        TemplateEngine engine = new TemplateEngine();
        Context ctx = new Context();
        ctx.setVariable("name", "World");
        String result = engine.process("Hello [[${name}]]!", ctx);
        System.out.println(result);
    }
}