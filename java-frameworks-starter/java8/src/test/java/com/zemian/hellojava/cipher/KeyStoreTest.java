package com.zemian.hellojava.cipher;

import com.zemian.hellojava.AppUtils;
import org.junit.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Properties;

import static com.zemian.hellojava.cipher.Crypto.AES_ALGORITHM;
import static com.zemian.hellojava.cipher.Crypto.toBytes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class KeyStoreTest {
    @Test
    public void keystore() throws Exception {
        // Reading a normal JKS keystore and fetch a privateKey entry.
        // We will use Crypto service to decrypt password readin from the app props so we can open the keystore.
        Crypto crypto = CryptoApp.getCrypto();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Properties props = AppUtils.loadAppProps();
        String privateKeyAlias = "mydomain";
        String keystoreResource = props.getProperty("app.keystore.file");
        String keystorePassword = props.getProperty("app.keystore.password");
        String privateKeyPassword = props.getProperty("app.keystore.key.mydomain.password");

        try (InputStream stream = cl.getResourceAsStream(keystoreResource)) {
            KeyStore keystore = KeyStore.getInstance("JKS");
            char[] keystorePasswordChars = crypto.decrypt(keystorePassword).toCharArray();
            keystore.load(stream, keystorePasswordChars);

            char[] privateKeyPasswordChars = crypto.decrypt(privateKeyPassword).toCharArray();
            Key privateKey = keystore.getKey(privateKeyAlias, privateKeyPasswordChars);
            assertThat(privateKey.getAlgorithm(), is("RSA"));
            assertThat(privateKey.getFormat(), is("PKCS#8"));
            assertThat(privateKey.getEncoded().length, is(1219));
        }
    }

    @Test
    public void secretKeyFromKeystore() throws Exception {
        // Reading a normal JCEKS keystore and fetch a secretKey entry.
        // We will use Crypto service to decrypt password readin from the app props so we can open the keystore.
        // After we obtain the secretKey, we will create another instance of Crypto from secretKey to encrypt data.
        Crypto crypto = CryptoApp.getCrypto();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Properties props = AppUtils.loadAppProps();
        String secretKeyAlias = "myaes256key";
        String keystoreResource = props.getProperty("app.keystore2.file");
        String keystorePassword = props.getProperty("app.keystore2.password");
        String secretKeyPassword = props.getProperty("app.keystore2.key.myaes256key.password");

        try (InputStream stream = cl.getResourceAsStream(keystoreResource)) {
            KeyStore keystore = KeyStore.getInstance("JCEKS");
            char[] keystorePasswordChars = crypto.decrypt(keystorePassword).toCharArray();
            keystore.load(stream, keystorePasswordChars);

            char[] secretKeyPasswordChars = crypto.decrypt(secretKeyPassword).toCharArray();
            SecretKey secretKey = (SecretKey) keystore.getKey(secretKeyAlias, secretKeyPasswordChars);

            Crypto c1 = new Crypto(AES_ALGORITHM,
                    secretKey,
                    new IvParameterSpec(toBytes("1234567890123456")));
            String secret = c1.encrypt("TEST");
            //System.out.println(secret);

            assertThat(c1.decrypt(secret), is("TEST"));

            // This c1 crypto should be unique and better not generate same result as other instance.
            assertThat(crypto.encrypt("TEST"), not(secret));
        }
    }
}
