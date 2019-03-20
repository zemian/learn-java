package com.zemian.hellojava.cipher;

import com.zemian.hellojava.AppException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Few built-in JDK hasher
 */
public class Hasher {
    public static String hash(String input) {
        return hash("MD5", input);
    }
    public static String sha(String input) {
        return hash("SHA-256", input);
    }
    public static String hash(String algorithm, String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            String hash = Base64.getEncoder().encodeToString(md.digest("test".getBytes(StandardCharsets.UTF_8)));
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new AppException("Failed to hash input with MD5", e);
        }
    }
}
