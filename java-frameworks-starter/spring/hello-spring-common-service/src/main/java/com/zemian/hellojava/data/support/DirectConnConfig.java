package com.zemian.hellojava.data.support;

import com.zemian.hellojava.CommonConfig;
import com.zemian.hellojava.cipher.Crypto;
import com.zemian.hellojava.cipher.CommonCryptoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import({CommonConfig.class, CommonCryptoConfig.class})
public class DirectConnConfig {

    @Autowired
    private Crypto crypto;

    @Autowired
    private Environment env;

    @Bean
    public DirectConnService directSybaseConnService() {
        String password = crypto.decrypt(env.getProperty("app.ds.password"));

        DirectConnService bean = new DirectConnService();
        bean.setUrl(env.getProperty("app.ds.url"));
        bean.setUsername(env.getProperty("app.ds.username"));
        bean.setPassword(password);

        return bean;
    }
}
