package zemian.junit5starter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    Calculator calc = new Calculator();

    @Test
    void add() {
        assertEquals(calc.add(1, 1), 2);
    }
}