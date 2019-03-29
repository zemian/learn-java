package zemian.junit5starter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Notice that test class and method no need to be public as in Junit4!
class HelloTest {
    Hello hello = new Hello();

    @Test
    void getMessage() {
        assertEquals("Hello World", hello.getMessage());
    }
}