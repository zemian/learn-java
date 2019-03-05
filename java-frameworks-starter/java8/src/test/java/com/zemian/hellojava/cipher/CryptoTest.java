package com.zemian.hellojava.cipher;

import com.zemian.hellojava.AppException;
import org.junit.Assert;
import org.junit.Test;

import java.security.Provider;
import java.security.Security;

import static com.zemian.hellojava.cipher.Crypto.AES_ALGORITHM;
import static com.zemian.hellojava.cipher.Crypto.AES_ECB_ALGORITHM;
import static com.zemian.hellojava.cipher.Crypto.DES_ALGORITHM;
import static com.zemian.hellojava.cipher.Crypto.DES_ECB_ALGORITHM;
import static com.zemian.hellojava.cipher.Crypto.toBytes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Use simple silly random key and iv values to test Crypto service.
 */
public class CryptoTest {
    private Crypto desCrypto = new Crypto(DES_ALGORITHM,
            toBytes("123456789012345678901234"), toBytes("12345678"));

    private Crypto aesCrypto = new Crypto(AES_ALGORITHM,
            toBytes("1234567890123456"), toBytes("1234567890123456"));

    @Test
    public void randString() {
        for (int i = 0; i < 10; i++) {
            String s = Crypto.randString(16);
            assertThat(s.length(), is(16));
            System.out.println(s);
        }
    }

    @Test
    public void desCrypto() {
        String secret = desCrypto.encrypt("TEST");
        assertThat(secret.length(), is(12));
        assertThat(desCrypto.decrypt(secret), is("TEST"));

        // Running it twice will produce same result!
        assertThat(desCrypto.encrypt("TEST"), is(secret));
    }

    @Test
    public void aseCrypto() {
        String secret = aesCrypto.encrypt("TEST");
        assertThat(secret.length(), is(24));
        assertThat(aesCrypto.decrypt(secret), is("TEST"));

        // Running it twice will produce same result!
        assertThat(aesCrypto.encrypt("TEST"), is(secret));
    }

    @Test
    public void cryptoEdgeCases() {
        String secret = aesCrypto.encrypt("");
        assertThat(secret.length(), is(24));
        assertThat(aesCrypto.decrypt(secret), is(""));

        try {
            aesCrypto.encrypt(null);
            Assert.fail("Encrypt should not process NULL value.");
        } catch (AppException e) {
            // Normal
        }

        try {
            aesCrypto.decrypt(null);
            Assert.fail("Decrypt should not process NULL value.");
        } catch (AppException e) {
            // Normal
        }
    }

    @Test
    public void cryptoLargeInput() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10_000; i++) {
            sb.append("0123456789");
        }
        String input = sb.toString();
        String encrypted  = aesCrypto.encrypt(input);
        assertThat(encrypted.length(), is(133356));
        assertThat(aesCrypto.decrypt(encrypted), is(input));
    }

    @Test
    public void aesEcb() {
        // Note: ECB does not accept IV Param
        Crypto c1 = new Crypto(AES_ECB_ALGORITHM, toBytes("1234567890123456"));
        String secret = c1.encrypt("TEST");
        assertThat(secret.length(), is(24));
        assertThat(c1.decrypt(secret), is("TEST"));
    }

    @Test
    public void desedeEcb() {
        // Note: ECB does not accept IV Param
        Crypto c1 = new Crypto(DES_ECB_ALGORITHM, toBytes("123456789012345678901234"));
        String secret = c1.encrypt("TEST");
        assertThat(secret.length(), is(12));
        assertThat(c1.decrypt(secret), is("TEST"));
    }

    //@Test
    public void securityProviders() {
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            System.out.println(provider.getInfo());
        }
    }
}