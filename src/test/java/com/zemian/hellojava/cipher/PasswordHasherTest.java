package com.zemian.hellojava.cipher;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class PasswordHasherTest {
    @Test
    public void createHash() throws Exception {
        // A salted hashed result should not be same as original value, and second call should be different
        // than first.
        assertThat(PasswordHasher.createHash("test"),
                both(not("test")).and(not(PasswordHasher.createHash("test"))));
    }

    @Test
    public void verifyHash() throws Exception {
        String hash = PasswordHasher.createHash("test");
        assertThat(PasswordHasher.verifyPassword("test", hash), is(true));
    }
}