package zemian.javastarter;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hello {
    private static final Logger LOG = Logger.getLogger(Hello.class.getName());
    
    public static void main(String ... args) throws Exception {
        LOG.log(Level.INFO, "Program started {}", new Date());
        LOG.log(Level.FINE, "Arguments: {}", Arrays.asList(args));
        LOG.log(Level.INFO, "Hello!");
        LOG.log(Level.FINER, "Program exiting now.");
        System.out.println("Say Hello to Java!");
    }
}