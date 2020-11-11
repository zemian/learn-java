package zemian.javastarter;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;

public class MathTest {
	@Test
	public void testJavaPrecision() {
		// Ensure Java/Computer binary double is expected to be un-precise
		double TOLERANCE = 0.000_000_001;
		double a = 5.6 + 5.8;
		assertThat(a, not(11.4));
		assertThat(Math.abs(11.4 - a), lessThanOrEqualTo(TOLERANCE));
	}
}