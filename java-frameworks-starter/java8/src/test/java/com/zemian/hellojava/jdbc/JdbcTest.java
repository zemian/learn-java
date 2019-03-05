package com.zemian.hellojava.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class JdbcTest {

    public static class Foo {
        public String a;
        public Integer b;
    }

    String table = JdbcTest.class.getSimpleName() +  "_foo";
    String url = System.getProperty("url", "jdbc:postgresql://localhost:5432/test");
    String user = System.getProperty("user", "test");
    String password = System.getProperty("password", "");

    @Test
    public void testConn() throws Exception {
        try (Connection conn =  DriverManager.getConnection(url, user, password)) {
            Jdbc jdbc = new Jdbc(conn);
            testJdbc(jdbc);
            Connection conn2 = jdbc.getConn();
            assertThat(conn2, notNullValue());
            assertThat(conn2, sameInstance(conn));
        }
    }

    @Test
    public void testDataSource() throws Exception {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(url);
        ds.setUser(user);
        ds.setPassword(password);
        Jdbc jdbc = new Jdbc(ds);
        testJdbc(jdbc);

        try (Connection conn = jdbc.getConn()) {
            try (Connection conn2 = jdbc.getConn()) {
                assertThat(conn, notNullValue());
                assertThat(conn2, notNullValue());
                assertThat(conn2, not(sameInstance(conn)));
            }
        }
    }

    private void testJdbc(Jdbc jdbc) {
        jdbc.withConn(conn -> {
            try (Statement stmt = conn.createStatement()) {
                String sql = "SELECT 1 + 1";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs.next()) {
                        Integer result = rs.getInt(1);
                        assertThat(result, is(2));
                    } else {
                        Assert.fail("Failed to execute simple query.");
                    }
                }
            }
            return null;
        });
    }

    @Test
    public void testUpdate() throws Exception {
        try (Connection conn =  DriverManager.getConnection(url, user, password)) {
            Jdbc jdbc = new Jdbc(conn);
            int createResult = -1, updateResult = -1;
            try {
                String countSql = "SELECT COUNT(*) FROM " + table;
                String sql = "CREATE TABLE " + table + "(a VARCHAR(100), b INT)";
                createResult = jdbc.execute(sql);
                assertThat(createResult, is(0));
                assertThat(jdbc.query(countSql), is(0L));

                sql = "INSERT INTO " + table + "(a, b) VALUES(?, ?)";
                updateResult = jdbc.execute(sql, "Hello1", 99);
                assertThat(updateResult, is(1));
                updateResult = jdbc.execute(sql, "Hello2", 98);
                assertThat(updateResult, is(1));
                updateResult = jdbc.execute(sql, "Hello3", 97);
                assertThat(updateResult, is(1));
                assertThat(jdbc.query(countSql), is(3L));

                sql = "DELETE FROM " + table + " WHERE b = ?";
                updateResult = jdbc.execute(sql, 99);
                assertThat(updateResult, is(1));
                assertThat(jdbc.query(countSql), is(2L));

                sql = "SELECT * FROM " + table + " WHERE b > ? ORDER BY a";
                List<Jdbc.Record> list = jdbc.query(sql, 10);
                assertThat(list.size(), is(2));
                assertThat(list.get(0).get("a"), is("Hello2"));
                assertThat(list.get(1).get("a"), is("Hello3"));

                sql = "SELECT * FROM " + table + "  ORDER BY a";
                List<Foo> foos = jdbc.query((rs, i) -> {
                    Foo f = new Foo();
                    f.a = rs.getString("a");
                    f.b = rs.getInt("b");
                    return f;
                }, sql);
                assertThat(foos.size(), is(2));
                assertThat(foos.get(0).a, is("Hello2"));
                assertThat(foos.get(1).a, is("Hello3"));
            } finally {
                if (createResult != -1) {
                    int result = jdbc.execute("DROP TABLE " + table);
                    assertThat(result, is(0));
                }
            }
        }
    }
}
