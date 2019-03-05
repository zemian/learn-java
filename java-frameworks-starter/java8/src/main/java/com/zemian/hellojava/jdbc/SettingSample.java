package com.zemian.hellojava.jdbc;

import java.security.Provider;
import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class SettingSample {
    public static void main(String[] args) throws Exception {
        String url = System.getProperty("url", "jdbc:postgresql://localhost:5432/test");
        String user = System.getProperty("user", "test");
        String password = System.getProperty("password", "");
        try (Connection conn =  DriverManager.getConnection(url, user, password)) {
            Jdbc jdbc = new Jdbc(conn);
            String action = System.getProperty("action", "query");
            switch (action) {
                case "delete":
                    delete(jdbc);
                    break;
                case "insert":
                    insert(jdbc);
                    break;
                case "query":
                    query(jdbc);
                    break;
            }
        }
    }

    public static void delete(Jdbc jdbc) {
        System.out.println("== Deleting data from table: settings ");
        String sql = "DELETE FROM settings";
        int c = jdbc.execute(sql);
        System.out.printf("%d records deleted.\n", c);
    }

    public static void query(Jdbc jdbc) {
        System.out.println("== List of Table: settings ");
        SettingDAO dao = new SettingDAO(jdbc);
        List<Setting> list = dao.findAll();
        for (Setting setting : list) {
            System.out.printf("Row{id=%d, category=%s, name=%s, value=%s}\n",
                    setting.getId(), setting.getCategory(), setting.getName(), setting.getValue());
        }
    }

    public static void insert(Jdbc jdbc) throws Exception {
        System.out.println("== Inserting samples into: settings ");
        jdbc.withConn(conn -> {
            String category;
            String sql = "INSERT INTO settings (category, name, value) VALUES(?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                category = "SYS_PROS";
                for (String name : System.getProperties().stringPropertyNames()) {
                    String value = System.getProperty(name);
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
