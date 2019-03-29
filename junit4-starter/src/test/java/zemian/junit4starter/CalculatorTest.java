package zemian.junit4starter;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class CalculatorTest {
    Calculator calc = new Calculator();

    @Test
    public void add() {
        MatcherAssert.assertThat(calc.add(1, 1), Matchers.is(2));
    }
}