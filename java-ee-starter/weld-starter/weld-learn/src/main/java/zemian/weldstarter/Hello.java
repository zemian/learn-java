package zemian.weldstarter;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Hello {
    public static void main(String[] args) {
        Weld weld = new Weld().beanClasses(Hello.class).disableDiscovery();
        // shutdown hook is registered automatically for WeldContainer
        WeldContainer container = weld.initialize();

        // Get app bean and invoke method
        Hello hello = container.select(Hello.class).get();
        hello.greeting();

        // Explicitly shutdown weld immediately
        // Not needed!
        //container.shutdown();
    }

    public String getMessage() {
        return "Hello World";
    }

    public void greeting() {
    	System.out.println(getMessage());

    	// Note that bean from Weld container are not simple direct instance as you would
        // use "new" operator! It's a proxy instead.
        System.out.println("This hello bean instance: " + this);
        System.out.println("This hello bean class: " + this.getClass());
        System.out.println("This hello bean classLoader: " + this.getClass().getClassLoader());
    }
}
