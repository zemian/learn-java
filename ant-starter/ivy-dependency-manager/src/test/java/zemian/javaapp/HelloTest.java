package zemian.javaapp;

import org.junit.Test;
import static org.junit.Assert.*;

public class HelloTest {
	@Test
	public void testHello() {
		Hello hello = new Hello();
		assertEquals("Hello World", hello.greet("World"));
	}
}