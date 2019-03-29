package com.zemian.hellojava;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.security.Provider;
import java.security.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class SettingSample {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext spring = null;
        try {
            spring = SpringApplication.run(AppSpringConfig.class, args);
            insert(spring.getBean(JdbcTemplate.class));
        } finally {
            if (spring != null) {
                spring.close();
            }
        }
    }

    public static void insert(JdbcTemplate jdbc) throws Exception {
        System.out.println("== Inserting samples into: settings ");
        jdbc.execute( (Connection conn) -> {
            String category;
            String sql = "INSERT INTO settings (category, name, value) VALUES(?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                category = "SYS_PROS";
                for (String name : System.getProperties().stringPropertyNames()) {
                    String value = System.getProperty(name);
                    if (value.length() > 5000) {
                        System.out.println("WARN: value too long, will truncate to 5000: " + name);
                        value = value.substring(0, 5000);
                    }
                    System.out.printf("Inserting %s: %s\n", category, name);
                    pstmt.setString(1, category);
                    pstmt.setString(2, name);
                    pstmt.setString(3, value);
                    pstmt.executeUpdate();
                }

                category = "ENV";
                for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    System.out.printf("Inserting %s: %s\n", category, name);
                    pstmt.setString(1, category);
                    pstmt.setString(2, name);
                    pstmt.setString(3, value);
                    pstmt.executeUpdate();
                }

                category = "LOCALE";
                for (Locale lc : Locale.getAvailableLocales()) {
                    System.out.printf("Inserting %s: %s\n", category, lc);
                    pstmt.setString(1, category);
                    pstmt.setString(2, lc.getDisplayName());
                    pstmt.setString(3, lc.toString());
                    pstmt.executeUpdate();
                }

                category = "TIMEZONE";
                for (String id : TimeZone.getAvailableIDs()) {
                    System.out.printf("Inserting %s: %s\n", category, id);
                    pstmt.setString(1, category);
                    pstmt.setString(2, id);
                    pstmt.setString(3, TimeZone.getTimeZone(id).toString());
                    pstmt.executeUpdate();
                }

                category = "ALGORITHM";
                for (Provider provider: Security.getProviders()) {
                    for (String key : provider.stringPropertyNames()) {
                        System.out.printf("Inserting %s: %s\n", category, key);
                        pstmt.setString(1, category);
                        pstmt.setString(2, provider.getName());
                        pstmt.setString(3, key);
                        pstmt.executeUpdate();
                    }
                }
            }
            return null;
        });
    }
}
