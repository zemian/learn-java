package zemian.junit5starter;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HelloTest {
    Hello hello = new Hello();

    @Test
    void getMessage() {
        assertThat(hello.getMessage(), "Hello World");
    }
}
