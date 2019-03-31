package zemian.javastarter;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HelloTest {
    Hello hello = new Hello();

    @Test
    void getMessage() {
        assertThat(hello.getMessage(), is("Hello World"));
    }
}
