package com.zemian.hellojava.spring.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import javax.naming.NamingEnumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LdapInspector {

    @Autowired
    private LdapTemplate ldap;

    public <T> T getAttr(String dn, String attrName) throws Exception {
        return ldap.lookup(dn, (AttributesMapper<T>) attrs -> {
            return (T) attrs.get(attrName).get();
        });
    }

    public Map<String, Object> getAttrsMap(String dn) {
        return ldap.lookup(dn, (AttributesMapper<Map<String, Object>>) attrs -> {
            Map<String, Object> ret = new HashMap<>();
            NamingEnumeration<String> names = attrs.getIDs();
            while(names.hasMoreElements()) {
                String name = names.nextElement();
                Object value = attrs.get(name).get();
                ret.put(name, value);
            }
            return ret;
        });
    }

    public void printAttrsMap(String dn) {
        Map<String, ?> map = getAttrsMap(dn);
        System.out.println("== LDAP Attricutes for dn='" + dn + "'");
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            System.out.println("  " + name + " = " + value + ", type=" + value.getClass());
        }
    }

    /** Print any base string listing and return the list. */
    public List<String> printLdapList(String base) {
        List<String> ret = ldap.list(base);
        System.out.println("== LDAP Listing for base='" + base + "', size=" + ret.size());
        for (String name : ret) {
            System.out.println("  " + name);
        }
        return ret;
    }

    /** Print top two levels of LDAP context */
    public void printLdapList() throws Exception {
        String base = getAttr("", "namingContexts");
        List<String> level1List = printLdapList(base);
        for (String level1Name : level1List) {
            String level2Base = level1Name + "," + base;
            printLdapList(level2Base);
        }
    }
}
