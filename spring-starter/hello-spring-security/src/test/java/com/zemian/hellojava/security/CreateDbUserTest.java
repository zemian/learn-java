package com.zemian.hellojava.security;

import com.zemian.hellojava.SpringTestBase;
import com.zemian.hellojava.data.CommonDataConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@ContextConfiguration(classes = {CommonDataConfig.class, AppSecurityConfig.class})
public class CreateDbUserTest extends SpringTestBase {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void createUsers() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);

//        userDetailsManager.createUser(createHashedUser("admin", "$2a$05$Exj9iNyCq.aDUVxi7HPp6egkFJbQF0BtWC1zgCusVB5AI/aMsakQG", "ADMIN"));
//        userDetailsManager.createUser(createHashedUser("test", "$2a$05$R9PsUlsdXRb3qUuIwdLR8u8X4ZVNn6I/o6SBTCYy98MGlKFXokCHW", "USER"));
//        userDetailsManager.createUser(createHashedUser("test2", "$2a$05$MtRC/pjZV/77BXW9WoCpCuNHuLPb5yDqaqQnX.0W3YbKN3kbToGZy", "USER"));
//
//        userDetailsManager.createUser(createEasyUser("test3", "C86YFGdwwUD8G43", false, "USER"));
//        // Update
//        UserDetails test3 = createEasyUser("test3", "test3", true, "USER");
//        userDetailsManager.updateUser(test3);

//        userDetailsManager.deleteUser("admin");
//        userDetailsManager.deleteUser("test");
//        userDetailsManager.deleteUser("test2");
//        userDetailsManager.deleteUser("test3");
    }

    private UserDetails createHashedUser(String username, String hashedPassword, String ... roles) {
        UserDetails ret = User.withUsername(username).password(hashedPassword).authorities(roles).build();
        return ret;
    }

    private UserDetails createEasyUser(String username, String plainPassword, boolean disabled, String ... roles) {
        String hash = passwordEncoder.encode(plainPassword);
        UserDetails ret = User.withUsername(username).password(hash).authorities(roles).disabled(disabled).build();
        return ret;
    }

    @Test
    public void passwordEncoder() {
        String hash;
        hash = passwordEncoder.encode("admin");
        assertThat(hash, not("admin"));
        hash = passwordEncoder.encode("test");
        assertThat(hash, not("test"));
        hash = passwordEncoder.encode("test2");
        assertThat(hash, not("test2"));
    }
}
