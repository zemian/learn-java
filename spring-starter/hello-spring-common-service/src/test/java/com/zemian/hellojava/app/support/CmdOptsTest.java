package com.zemian.hellojava.app.support;

import com.zemian.hellojava.support.CmdOpts;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class CmdOptsTest {
    @Test
    public void parseArgs() {
        // Empty args
        CmdOpts opts = new CmdOpts(new String[]{});
        assertThat(opts.getArgs().size(), is(0));
        assertThat(opts.getOpt("test"), nullValue());

        try {
            opts.getArgOrError(1, "Missing args[0]");
            Assert.fail("You should not have args[0] here.");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Some args
        opts = new CmdOpts(new String[]{"a", "b", "color"});
        assertThat(opts.getArgs().size(), is(3));
        assertThat(opts.getArg(0), is("a"));
        assertThat(opts.getArg(1), is("b"));
        assertThat(opts.getArg(2), is("color"));
        assertThat(opts.getOpt("test"), nullValue());

        try {
            opts.getArgOrError(3, "Missing args[3]");
            Assert.fail("You should not have args[3] here.");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void parseOptions() {
        // Options and arguments
        CmdOpts opts = new CmdOpts(new String[]{"--debug=true", "-n=100", "--name=foo", "a", "b", "color"});
        assertThat(opts.getArgs().size(), is(3));
        assertThat(opts.getArg(0), is("a"));
        assertThat(opts.getArg(1), is("b"));
        assertThat(opts.getArg(2), is("color"));
        assertThat(opts.getOpt("test"), nullValue());
        assertThat(opts.getBooleanOpt("debug"), is(true));
        assertThat(opts.getBooleanOpt("debug2", false), is(false));
        assertThat(opts.getIntOpt("n"), is(100));
        assertThat(opts.getIntOpt("n2", -1), is(-1));
        assertThat(opts.getOpt("name"), is("foo"));
        assertThat(opts.getOpt("name2", "bar"), is("bar"));

        try {
            opts.getArgOrError(3, "Missing args[3]");
            Assert.fail("You should not have args[3] here.");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void parseMultiOptions() {
        // NOTE You can not pass in "--Dfoo=bar", "--Dfoo2=bar2" pattern!
        CmdOpts opts = new CmdOpts(new String[]{"--var=foo#bar", "--var=foo2#bar2"});
        List<String> list = opts.getMultiOpts("var");
        assertThat(list, hasItems("foo#bar", "foo2#bar2"));
    }
}
