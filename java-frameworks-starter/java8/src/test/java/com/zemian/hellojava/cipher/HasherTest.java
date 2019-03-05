package com.zemian.hellojava.cipher;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HasherTest {

    @Test
    public void md5() throws Exception {
        assertThat(Hasher.hash("test"), is("CY9rzUYh03PK3k6DJie09g=="));
        assertThat(Hasher.hash("test"), is("CY9rzUYh03PK3k6DJie09g=="));
    }

    @Test
    public void sha() throws Exception {
        assertThat(Hasher.sha("test"), is("n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg="));
        assertThat(Hasher.sha("test"), is("n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg="));
    }

}