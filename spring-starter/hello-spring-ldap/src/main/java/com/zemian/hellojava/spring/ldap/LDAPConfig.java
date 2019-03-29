package com.zemian.hellojava.spring.ldap;

import com.zemian.hellojava.CommonConfig;
import com.zemian.hellojava.cipher.Crypto;
import com.zemian.hellojava.cipher.CommonCryptoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@ComponentScan
@Import({CommonConfig.class, CommonCryptoConfig.class})
public class LDAPConfig {
    @Autowired
    private Environment env;

    @Autowired
    private Crypto crypto;

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource bean = new LdapContextSource();
        bean.setUrl(env.getProperty("app.ldap.url"));

        String user = env.getProperty("app.ldap.userDn", (String) null);
        if (user != null) {
            String password = crypto.decrypt(env.getProperty("app.ldap.password"));
            bean.setUserDn(user);
            bean.setPassword(password);
        } else {
            bean.setAnonymousReadOnly(true);
        }

        return bean;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        LdapTemplate bean = new LdapTemplate();
        bean.setContextSource(ldapContextSource());
        return bean;
    }
}
