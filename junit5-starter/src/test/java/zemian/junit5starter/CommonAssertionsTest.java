package zemian.junit5starter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CommonAssertionsTest {
    @Test
    void equalsOrNot() {
        assertEquals("foobar", "foo" + "bar");
        assertNotEquals("foo bar", "foo" + "bar");
    }

    @Test
    void arrayEquals() {
        // Normal splits
        assertArrayEquals(new String[]{"a", "b", "c"}, "a b c".split(" "));
        assertArrayEquals(new String[]{"a", "b"}, "a b".split(" "));
        assertArrayEquals(new String[]{"a"}, "a".split(" "));

        // Separator on left or right padding
        assertArrayEquals(new String[]{"a"}, "a ".split(" "));
        assertArrayEquals(new String[]{"", "a"}, " a".split(" "));
        assertArrayEquals(new String[]{"", "a"}, " a ".split(" "));

        // No separator matched
        assertArrayEquals(new String[]{"xx"}, "xx".split(" "));

        // NOTE: It's empty array if only one separator exists!
        assertArrayEquals(new String[]{}, " ".split(" "));
        assertArrayEquals(new String[]{}, "xx".split("xx"));
    }

    @Test
    void arrayContains() {
        String[] actual = "d a b c".split(" ");
        String[] expected = {"a", "b"};
        HashSet<String> actualSet = new HashSet<>(Arrays.asList(actual));
        HashSet<String> expectedSet = new HashSet<>(Arrays.asList(expected));
        assertTrue(actualSet.containsAll(expectedSet));
    }

    @Test
    void sameInstance() {
        Object actualObj = new Object();
        Object expectedObj = actualObj;
        assertSame(actualObj, expectedObj);
        assertNotSame(actualObj, new Object());
    }
}
