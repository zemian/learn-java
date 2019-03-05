package com.zemian.hellojava.cipher;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CryptoAppTest  {
    @Test
    public void test() {
        Crypto crypto = CryptoApp.getCrypto();
        System.out.println(crypto.encrypt("TEST"));
        assertThat(1 + 1, is(2));
    }
}
