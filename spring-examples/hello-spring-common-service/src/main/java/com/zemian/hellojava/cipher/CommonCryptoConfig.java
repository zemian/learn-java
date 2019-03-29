package com.zemian.hellojava.cipher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.zemian.hellojava.cipher.Crypto.toBytes;

@Configuration
public class CommonCryptoConfig {
    @Value("${app.crypto.algorithm}")
    private String cryptoAlgorithm;
    @Value("${app.crypto.key}")
    private String cryptoKey;
    @Value("${app.crypto.ivParam}")
    private String cryptoIvParam;
    @Bean
    public Crypto crypto() {
        Crypto bean = new Crypto(Crypto.AES_ALGORITHM, toBytes(cryptoKey), toBytes(cryptoIvParam));
        return bean;
    }

    @Bean
    public PasswordHasher passwordHasher() {
        return new PasswordHasher();
    }
}
