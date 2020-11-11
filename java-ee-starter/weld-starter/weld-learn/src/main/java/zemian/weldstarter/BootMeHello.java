package zemian.weldstarter;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class BootMeHello {
    @Inject
    Hello hello;

    public void startMainByWeld(@Observes ContainerInitialized event,
                                @Parameters List<String> parameters) {
        System.out.println("We are been bootstrapped by Weld.");
        System.out.println("ContainerInitialized event=" + event);
        System.out.println("parameters=" + parameters);

        System.out.println("Let's say a greeting.");
        hello.greeting();
        // Hum... notice that the class instance is a non-proxy! Why?
    }
}
