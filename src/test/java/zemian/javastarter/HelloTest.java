package zemian.javastarter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HelloTest {
	/** Verify junit test is setup correctly. */
    @Test void main() {
    	try {
    		Hello.main(new String[]{});
    	} catch (Exception e) {
    		fail("Hello program should not throw exception.", e);
    	}
    }

	/** Java float-point rules. */
    @Test void floatingPoint() {
    	assertNotEquals(0.3, 0.1 + 0.2);
    	assertEquals(0.3, 0.1 + 0.2, 0.000001);
    }
}