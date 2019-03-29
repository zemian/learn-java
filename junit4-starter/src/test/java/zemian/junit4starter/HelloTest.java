package zemian.junit4starter;

import static org.junit.Assert.*;
import org.junit.Test;

public class HelloTest {
    Hello hello = new Hello();

    @Test
    public void getMessage() {
        assertEquals("Hello World", hello.getMessage());
    }
}