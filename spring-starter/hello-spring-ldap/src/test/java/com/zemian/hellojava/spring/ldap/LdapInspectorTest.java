package com.zemian.hellojava.spring.ldap;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = LDAPConfig.class)
@ComponentScan
public class LdapInspectorTest extends SpringTestBase {
    @Autowired
    private LdapInspector ldapInspector;

    @Test
    public void printAttributes() throws Exception {
        ldapInspector.printAttrsMap("");
        ldapInspector.printAttrsMap("o=mydomain.com");
    }

    @Test
    public void listLdap() throws Exception {
        ldapInspector.printLdapList();
    }
}
