package zemian.hellojava;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptsTest {
    @Test
    void getOpts() {
        Opts opts;

        opts = new Opts(new String[]{});
        assertEquals(opts.getOpts().size(), 0);
        assertEquals(opts.getArgs().size(), 0);

        opts = new Opts("aaa".split(" "));
        assertEquals(opts.getOpts().size(), 0);
        assertIterableEquals(opts.getArgs(), List.of("aaa"));

        opts = new Opts(new String[]{"-d"});
        assertEquals(opts.getOpts().size(), 1);
        assertEquals(opts.getOpts().get("d"), true);
        assertIterableEquals(opts.getArgs(), List.of());

        opts = new Opts(new String[]{"--debug"});
        assertEquals(opts.getOpts().size(), 1);
        assertEquals(opts.getOpts().get("debug"), true);
        assertIterableEquals(opts.getArgs(), List.of());


        opts = new Opts(new String[]{"--file=test.txt"});
        assertEquals(opts.getOpts().size(), 1);
        assertEquals(opts.getOpts().get("file"), "test.txt");
        assertIterableEquals(opts.getArgs(), List.of());


        opts = new Opts("-v aaa 123".split(" "));
        assertEquals(opts.getOpts().size(), 1);
        assertEquals(opts.getOpts().get("v"), true);
        assertIterableEquals(opts.getArgs(), List.of("aaa", "123"));

        opts = new Opts("-v --log=system aaa 123".split(" "));
        assertEquals(opts.getOpts().size(), 2);
        assertEquals(opts.getOpts().get("v"), true);
        assertEquals(opts.getOpts().get("log"), "system");
        assertIterableEquals(opts.getArgs(), List.of("aaa", "123"));

        opts = new Opts("-v --log=system --foo=bar aaa 123 bbb".split(" "));
        assertEquals(opts.getOpts().size(), 3);
        assertEquals(opts.getOpts().get("v"), true);
        assertEquals(opts.getOpts().get("foo"), "bar");
        assertEquals(opts.getOpts().get("log"), "system");
        assertIterableEquals(opts.getArgs(), List.of("aaa", "123", "bbb"));

        opts = new Opts("-v --log=system --foo=bar".split(" "));
        assertEquals(opts.getOpts().size(), 3);
        assertEquals(opts.getOpts().get("v"), true);
        assertEquals(opts.getOpts().get("foo"), "bar");
        assertEquals(opts.getOpts().get("log"), "system");
        assertIterableEquals(opts.getArgs(), List.of());

        opts = new Opts("aaa 123".split(" "));
        assertEquals(opts.getOpts().size(), 0);
        assertIterableEquals(opts.getArgs(), List.of("aaa", "123"));
    }

    @Test
    void specialCases() {
        Opts opts;

        opts = new Opts("--password=".split(" "));
        assertEquals(opts.getOpts().size(), 1);
        assertEquals(opts.getOpts().get("password"), "");
        assertIterableEquals(opts.getArgs(), List.of());

    }
}