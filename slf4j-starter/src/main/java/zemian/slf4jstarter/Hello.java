package zemian.slf4jstater;

import org.slf4j.*;
import java.util.*;

public class Hello {
    public static final Logger LOG = LoggerFactory.getLogger(Hello.class);
    
    public static void main(String ... args) throws Exception {
        LOG.info("You should enable lower logging level to see more.");
        LOG.trace("Program started {}", new Date());
        LOG.debug("Arguments: {}", Arrays.asList(args));
        System.out.println("Hello World!");
        LOG.trace("Program exiting now.");
    }
}