package zemian.logbackstarter;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.slf4j.*;


class HelloTest {
	private static Logger log = LoggerFactory.getLogger(HelloTest.class);
    Hello hello = new Hello();

    @Test
    void getMessage() {
    	log.debug("Testing Hello message");
        assertThat(hello.getMessage(), is("Hello World"));
    }
}
