package com.zemian.hellojava.cipher;

import com.zemian.hellojava.SpringTestBase;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = CommonCryptoConfig.class)
public class CryptoTest extends SpringTestBase {
    @Autowired
    private Crypto crypto;

    @Test
    public void generateIDs() {
        MatcherAssert.assertThat(Crypto.randString(16).length(), is(16));
        MatcherAssert.assertThat(Crypto.randString(32).length(), is(32));
        MatcherAssert.assertThat(Crypto.randString(64).length(), is(64));
        MatcherAssert.assertThat(Crypto.randString(128).length(), is(128));
        MatcherAssert.assertThat(Crypto.randString(256).length(), is(256));
    }

    @Test
    public void encrypt() {
        String secret = crypto.encrypt("test");
        //System.out.println(secret);
        MatcherAssert.assertThat(crypto.decrypt(secret), is("test"));
    }
}
