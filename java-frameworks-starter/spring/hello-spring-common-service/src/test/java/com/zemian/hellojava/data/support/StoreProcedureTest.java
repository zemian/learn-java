package com.zemian.hellojava.data.support;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = DirectConnConfig.class)
public class StoreProcedureTest extends SpringTestBase {
    @Autowired
    private DirectConnService connService;

    @Test
    public void storeProcedureSimple() {
        connService.execute(conn -> {
            String sql = "{? = call test_sp_simple}";
            try(CallableStatement cs = conn.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.INTEGER);
                cs.executeUpdate();
                int result = cs.getInt(1);
                assertThat(result, is(2));
            }
            return null;
        });

        // Postgres recommend to invoke function as SELECT
        connService.execute(conn -> {
            String sql = "SELECT test_sp_simple()";
            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                try(ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int result = rs.getInt(1);
                        assertThat(result, is(2));
                    }
                }
            }
            return null;
        });
    }

    @Test
    public void storeProcedureInc() {
        connService.execute(conn -> {
            String sql = "{? = call test_sp_inc(?)}";
            try(CallableStatement cs = conn.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.INTEGER);
                cs.setInt(2, 99);
                cs.executeUpdate();
                int result = cs.getInt(1);
                assertThat(result, is(100));
            }
            return null;
        });

        // Postgres recommend to invoke function as SELECT
        connService.execute(conn -> {
            String sql = "SELECT test_sp_inc(?)";
            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, 99);
                try(ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int result = rs.getInt(1);
                        assertThat(result, is(100));
                    }
                }
            }
            return null;
        });
    }

    private void printSetting(ResultSet rs) throws Exception {
        System.out.println("Result : " + rs.getString("setting_id") +
                ", " + rs.getString("category") +
                ", " + rs.getString("name") +
                ", " + rs.getString("value"));
    }
    private void printLog(ResultSet rs) throws Exception {
        System.out.println("Result : " + rs.getString("log_id") +
                ", " + rs.getString("name") +
                ", " + rs.getString("value"));
    }

    @Test
    public void storeProcedureSelectCursor() {
        // CURSOR is the only time postgres jdbc recommend to use call statment
        connService.execute(conn -> {
            conn.setAutoCommit(false); // Needed to receive REFCURSOR
            String sql = "{? = call test_sp_sel_settings()}";
            try (CallableStatement cs = conn.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.OTHER);
                cs.execute();
                try (ResultSet rs = cs.getObject(1, ResultSet.class)) {
                    while (rs.next()) {
                        printSetting(rs);
                    }
                }
            }
            return null;
        });
    }

    @Test
    public void storeProcedureSelectSetof() {
        connService.execute(conn -> {
            try (Statement stmt = conn.createStatement()) {
                String sql = "SELECT * FROM test_sp_sel_settings2()";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        printSetting(rs);
                    }
                }
            }
            return null;
        });
    }

    @Test
    public void storeProcedureOutParam() {
        connService.execute(conn -> {
            {
                String sql = "SELECT test_sp_ins_settings(?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, "SP_TEST");
                    stmt.setString(2, "foo");
                    stmt.setString(3, "bar");

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            int maxId = rs.getInt(1);
                            assertThat(maxId, greaterThanOrEqualTo(1));
                        }
                    }
                }
            }

            {
                String sql = "SELECT * FROM test_sp_count_by_out_param(?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, "SP_TEST");
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            long count = rs.getLong(1);
                            assertThat(count, greaterThanOrEqualTo(1L));
                        }
                    }
                }
            }

            {
                String sql = "SELECT * FROM test_sp_count_by_out_param2(?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, "SP_TEST");
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            long count = rs.getLong(1);
                            int maxId = rs.getInt(2);
                            assertThat(count, greaterThanOrEqualTo(1L));
                            assertThat(maxId, greaterThanOrEqualTo(1));
                        }
                    }
                }
            }
            try(Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM settings WHERE category = 'SP_TEST'");
            }
            return null;
        });
    }

    @Test
    public void storeProcedureInsert2() {
        connService.execute(conn -> {
            {
                String sql = "SELECT test_sp_ins_settings2(?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, "SP_TEST");
                    stmt.setString(2, "foo");
                    stmt.setString(3, "bar");
                    stmt.execute(); // NOTE executeUpdate will not work since above func does not return value!
                }
            }

            try(Statement stmt = conn.createStatement()) {
                String sql = "SELECT * FROM settings WHERE category = 'SP_TEST'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        printSetting(rs);
                    }
                }
            }
            try(Statement stmt = conn.createStatement()) {
                String sql = "SELECT * FROM audit_logs WHERE name = 'test_sp_ins_settings2'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        printLog(rs);
                    }
                }
            }

            try(Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM settings WHERE category = 'SP_TEST'");
                stmt.executeUpdate("DELETE FROM audit_logs WHERE name = 'test_sp_ins_settings2'");
            }
            return null;
        });
    }
}
