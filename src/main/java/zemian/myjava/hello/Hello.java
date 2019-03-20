package zemian.myjava.hello;

import org.slf4j.*;

import java.util.*;

public class Hello {
    public static final Logger LOG = LoggerFactory.getLogger(Hello.class);
    
    public static void main(String ... args) throws Exception {
        LOG.trace("Program started {}", new Date());
        LOG.debug("Arguments: {}", Arrays.asList(args));
        LOG.info("Hello!");
        LOG.trace("Program exiting now.");
    }
}