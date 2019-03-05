package zemian.myjava.hello;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class HelloTest {
    @Test
    public void testHello() {
        assertThat("He" + "llo", is("Hello"));
    }
}