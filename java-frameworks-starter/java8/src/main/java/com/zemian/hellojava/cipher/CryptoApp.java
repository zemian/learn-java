package com.zemian.hellojava.cipher;

import com.zemian.hellojava.AppUtils;

import java.util.Properties;

import static com.zemian.hellojava.cipher.Crypto.toBytes;

/**
 * An app to encrypt plain text into secret string for save storing passwords.
 *
 * Usage: CryptoApp myscret
 */
public class CryptoApp {
    public static void main(String[] args) {
        System.out.println(CryptoApp.getCrypto().encrypt(args[0]));
    }

    private static Crypto crypto;
    public static Crypto getCrypto() {
        if (crypto == null) {
            Properties props = AppUtils.loadAppProps();
            String algorithm = props.getProperty("app.crypto.algorithm");
            String key = props.getProperty("app.crypto.key");
            String ivParam = props.getProperty("app.crypto.ivParam");
            crypto = new Crypto(algorithm, toBytes(key), toBytes(ivParam));
        }
        return crypto;
    }
}
