package zemian.mysqljdbc;

import java.sql.*;
public class TestDB {
    public static void main(String[] args) throws Exception {
        String url = System.getProperty("url", "jdbc:mysql://localhost:3306/mysql");
        String user = System.getProperty("user", "root");
        String password = System.getProperty("password", "");
        String sql = System.getProperty("sql", "SELECT VERSION()");

        System.out.println("Testing MySQL DB with user=" + user + " and url=" + url);
        System.out.println("SQL=" + sql);
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        System.out.println(rs.getObject(1));
                    }
                }
            }
        }
    }
}