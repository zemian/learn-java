package zemian.myjava.hello;

import java.util.*;
import org.apache.logging.log4j.*;

public class Hello {
    public static final Logger LOG = LogManager.getLogger(Hello.class);
    
    public static void main(String ... args) throws Exception {
        LOG.trace("Program started {}", new Date());
        LOG.debug("Arguments: {}", Arrays.asList(args));
        LOG.info("Hello!");
        LOG.trace("Program exiting now.");
    }
}