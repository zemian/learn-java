package com.zemian.toolbox.codegen.support;

import com.zemian.toolbox.support.JavaUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CodeGenUtilsTest {
    @Test
    public void underscoreToCamel() {
        assertThat(JavaUtils.underscoreToCamel("hello_world"), is("helloWorld"));
        assertThat(JavaUtils.underscoreToCamel("hello_world_again"), is("helloWorldAgain"));
        assertThat(JavaUtils.underscoreToCamel("hello"), is("hello"));
        assertThat(JavaUtils.underscoreToCamel("hello_a"), is("helloA"));
        assertThat(JavaUtils.underscoreToCamel("hello_a_b"), is("helloAB"));
    }

    @Test
    public void camelToUnderscore() {
        assertThat(JavaUtils.camelToUnderscore("helloWorld"), is("hello_world"));
        assertThat(JavaUtils.camelToUnderscore("helloWorldAgain"), is("hello_world_again"));
        assertThat(JavaUtils.camelToUnderscore("hello"), is("hello"));
        assertThat(JavaUtils.camelToUnderscore("helloA"), is("hello_a"));
        assertThat(JavaUtils.camelToUnderscore("helloAB"), is("hello_a_b"));
    }

    @Test
    public void urlNameToPackage() {
        assertThat(JavaUtils.urlNameToPackage("hello-java"), is("hello.java"));
        assertThat(JavaUtils.urlNameToPackage("hello-java_service"), is("hello.java.service"));
    }
}
