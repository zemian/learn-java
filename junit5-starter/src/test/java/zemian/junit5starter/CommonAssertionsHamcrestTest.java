package zemian.junit5starter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CommonAssertionsHamcrestTest {
    @Test
    void equalsOrNot() {
        assertThat("foo" + "bar", is("foobar"));
        assertThat("foo" + "bar", not("foo bar"));
    }

    @Test
    void arrayEquals() {
        // Normal splits
        assertThat("a b c".split(" "), arrayContaining("a", "b", "c"));
        assertThat("a b".split(" "), arrayContaining("a", "b"));
        assertThat("a".split(" "), arrayContaining("a"));

        // Separator on left or right padding
        assertThat("a ".split(" "), arrayContaining("a"));
        assertThat(" a".split(" "), arrayContaining("", "a"));
        assertThat(" a ".split(" "), arrayContaining("", "a"));

        // No separator matched
        assertThat("xx".split(" "), arrayContaining("xx"));

        // NOTE: It's empty array if only one separator exists!
        assertThat(" ".split(" "), emptyArray());
        assertThat("xx".split("xx"), emptyArray());
    }

    @Test
    void arrayContains() {
        assertThat(Arrays.asList("d a b c".split(" ")), hasItems("a", "b"));
    }


    @Test
    void sameInstance() {
        Object actualObj = new Object();
        Object expectedObj = actualObj;
        assertThat(actualObj, is(expectedObj));
        assertThat(actualObj, not(new Object()));
    }
}
