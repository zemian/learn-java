package zemian.mpstarter.helidonstarter;


import zemian.mpstarter.helidonstarter.config.ConfigTestController;


import zemian.mpstarter.helidonstarter.resilient.ResilienceController;


import zemian.mpstarter.helidonstarter.metric.MetricController;


import zemian.mpstarter.helidonstarter.secure.ProtectedController;
import org.eclipse.microprofile.auth.LoginConfig;
import javax.annotation.security.DeclareRoles;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ApplicationPath("/data")

@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({"protected"})

@ApplicationScoped
public class HelidonstarterRestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<>();

        // resources
        classes.add(HelloController.class);
        
        classes.add(ConfigTestController.class);
        
        
        classes.add(ResilienceController.class);
        
        
        classes.add(MetricController.class);
        
        
        classes.add(ProtectedController.class);
        

        return classes;
    }

}
