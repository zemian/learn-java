package zemian.myjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloTest {
    @Test
    public void testHello() {
        assertEquals("Hello", "He" + "llo");
    }
}