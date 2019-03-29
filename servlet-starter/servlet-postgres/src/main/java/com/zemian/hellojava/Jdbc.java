package com.zemian.hellojava;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * A simple template class to help write JDBC related operations.
 *
 * You may create an instance with either DataSource or direct Connection.
 * If you use DataSource, then it will get a connection from it on each operation, and it
 * will auto close upon complete. This is usally good use case when using a DB pool.
 * Else if you use direct Connection, then it will reuse same connection for all operations,
 * but the user will have to responsible to close the connection.
 */
public class Jdbc {

    //
    // Callback and action interfaces
    //
    public interface ConnFunction<T> {
        T apply(Connection conn) throws Exception;
    }

    public interface ResultSetMapper<T> {
        T apply(ResultSet rs, int rowNum) throws Exception;
    }

    //
    // Jdbc Instance management
    //
    private DataSource dataSource;
    private Connection conn;

    public Jdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Jdbc(Connection conn) {
        this.conn = conn;
    }

    protected Connection getConnection() {
        if (conn != null) {
            return conn;
        } else {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                throw new AppException("Failed to get new DB connection", e);
            }
        }
    }

    //
    // Utility methods
    //

    protected void bindParams(PreparedStatement stmt, Object[] params) throws SQLException {
        if (params.length > 0) {
            int i = 1;
            for (Object param : params) {
                stmt.setObject(i++, param);
            }
        }
    }

    protected Set<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int colSize = md.getColumnCount();
        Set<String> result = new HashSet<>();
        for (int i = 1; i <= colSize; i++) {
            result.add(md.getColumnName(i));
        }
        return result;
    }

    //
    // SQL Operations methods
    //


    /**
     * A Connection callback that user can perform any JDBC operations.
     */
    public <T> T execute(ConnFunction<T> action) {
        try {
            if (conn != null) {
                return action.apply(conn);
            } else {
                try (Connection conn = getConnection()) {
                    return action.apply(conn);
                }
            }
        } catch (Exception e) {
            throw new AppException("Failed to access database.", e);
        }
    }

    /**
     * Execute update SQL. If parames is given it will use a PreparedStatement, else will use
     * a plain Statement.
     */
    public int update(String sql, Object ... params) {
        return execute(conn -> {
            if (params.length > 0) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    bindParams(stmt, params);
                    return stmt.executeUpdate();
                }
            } else {
                try (Statement stmt = conn.createStatement()) {
                    return stmt.executeUpdate(sql);
                }
            }
        });
    }

    /**
     * Query single object and auto cast to receiver type. We expect SQL query to select single column
     * item and there should only be one record in result. All other condition will result in a exception.
     */
    @SuppressWarnings("unchecked")
    public <T> T query(String sql, Object ... params) {
        return execute(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                bindParams(stmt, params);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Object result = rs.getObject(1);

                    // Ensure we have unique row
                    if (rs.next())
                        throw new AppException("No unique record found.");

                    return (T)result;
                } else {
                    throw new AppException("No record found.");
                }
            }
        });
    }

    /**
     * Query and return a List of Custom Mapped records found in a SQL.
     */
    public <T> List<T> query(ResultSetMapper<T> mapper, String sql, Object ... params) {
        return execute(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                bindParams(stmt, params);
                ResultSet rs = stmt.executeQuery();
                List<T> result = new ArrayList<>();
                int rowNum = 1;
                while (rs.next()) {
                    T row = mapper.apply(rs, rowNum++);
                    result.add(row);
                }
                return result;
            }
        });
    }

    /**
     * Query and return a List of Map from all records found in a SQL.
     */
    public List<Map<String, Object>> queryList(String sql, Object ... params) {
        return execute(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                bindParams(stmt, params);
                ResultSet rs = stmt.executeQuery();
                Set<String> cols = getColumnNames(rs);
                List<Map<String, Object>> result = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (String col : cols) {
                        row.put(col, rs.getObject(col));
                    }
                    result.add(row);
                }
                return result;
            }
        });
    }
}
