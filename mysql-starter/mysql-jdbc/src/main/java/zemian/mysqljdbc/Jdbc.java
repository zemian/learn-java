package zemian.mysqljdbc;


import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provide an easier interface to run SQLs and retrieve data than direct Java JDBC API.
 *
 * You can instantiate this class in two ways:
 *
 * - Method1: Using a DataSource. Each operation will get a Connection object from the #dataSource.
 * - Method2: Using a direct Connection object. Use this to reuse a single connection to perform all
 *            JDBC operations.
 *
 * Both methods above will not directly close the Connection object per Jdbc instance. It's user
 * responsibility to close it after use. For pooled DataSource, it should be automatic, else you
 * might want to use the Jdbc.withJdbc() convenient method. This method will let you quickly start a single
 * new DB connection and Jdbc instance, and it will auto close connection when method is exited.
 *
 * This class provide these easy methods to work with DB data:
 * - #execute() perform update SQLs that returns number of records updated.
 * - #query() perform select SQLs that returns a list of records.
 * - #get() perform select SQLs that return a single object element.
 * - #insert() perform table insert using a Map as record.
 * - #update() perform table update using a Map as record.
 *
 * The #query() and #get() methods allow you to provide custom RSMapper to convert ResultSet instance
 * into any user object.
 *
 * @author Zemian Deng 2018-07-28
 */
public class Jdbc {
    // == Static utility methods - Loading props and instantiate Jdbc instance.
    public static Properties loadProperties(String resName) {
        Properties props = new Properties();
        URL res = Jdbc.class.getResource(resName);
        if (res == null)
            throw new RuntimeException("Resource not found: " + resName);
        try (InputStream ins = res.openStream()) {
            props.load(ins);
        } catch (IOException e) {
            throw new RuntimeException("Unable to open properties from resource: " + resName, e);
        }
        return props;
    }
    public static void withJdbc(String jdbcPropsResource, JdbcAction task) {
        if (!jdbcPropsResource.startsWith("/"))
            jdbcPropsResource = "/" + jdbcPropsResource;
        withJdbc(loadProperties(jdbcPropsResource), task);
    }
    public static void withJdbc(Properties jdbcProps, JdbcAction task) {
        try (Connection conn = DriverManager.getConnection(jdbcProps.getProperty("url"), jdbcProps)) {
            Jdbc jdbc = new Jdbc(conn);
            task.runJdbc(jdbc);
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException("Failed to process Jdbc", e);
        }
    }

    // == Static utility methods - JDK JDBC API Support
    public static List<String> getFieldNames(ResultSet rs) throws SQLException {
        List<String> names = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        for (int i = 1, max = md.getColumnCount(); i <= max; i++) {
            names.add(md.getColumnName(i));
        }
        return names;
    }

    public static void setParams(PreparedStatement stmt, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    // == Static inner interfaces for callback data processing
    interface JdbcAction {
        void runJdbc(Jdbc jdbc) throws Exception;
    }

    interface ConnAction<T> {
        T runConn(Connection conn) throws Exception;
    }

    interface ConnActionNoRet {
        void runConn(Connection conn) throws Exception;
    }

    interface RSMapper<T> {
        T map(ResultSet rs, int idx) throws Exception;
    }

    // == Static data record POJO for easy data holding
    public static class Record extends HashMap<String, Object> {
        public Record() {}
        public Record(ResultSet rs) throws SQLException {
            this(rs, Jdbc.getFieldNames(rs));
        }
        public Record(ResultSet rs, List<String> names) throws SQLException {
            for (String name : names) {
                Object val = rs.getObject(name);
                put(name, val);
            }
        }
        public Record(Object ... entries) {
            for (int i = 0; i < entries.length; i+= 2) {
                Object key = entries[i];
                Object val = entries[i + 1];
                put(key.toString(), val);
            }
        }
        @SuppressWarnings("unchecked")
        public <T> T getValue(String key) {
            return (T) get(key);
        }
        public List<String> getFieldNames() {
            return new ArrayList<>(keySet());
        }
        @Override
        public String toString() {
            return "Record" + super.toString();
        }
    }

    // == Jdbc Service implementation
    private DataSource dataSource;
    private Connection conn;

    public Jdbc(Connection conn) {
        this.conn = conn;
    }

    public Jdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConn() throws SQLException {
        if (conn != null) {
            return conn;
        } else {
            return dataSource.getConnection();
        }
    }

    public <T> T withConn(ConnAction<T> action) {
        try {
            return action.runConn(getConn());
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException("Failed to process DB conn", e);
        }
    }

    public void withConnNoRet(ConnActionNoRet action) {
        withConn(conn -> {
            action.runConn(conn);
            return null;
        });
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String sql, Object ... params) {
        return get((rs, idx) -> (T) rs.getObject(1), sql, params);
    }

    public <T> T get(RSMapper<T> mapper, String sql, Object ... params) {
        return withConn(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParams(stmt, params);
                stmt.executeQuery();
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        return mapper.map(rs, 0);
                    }
                }
            }
            throw new RuntimeException("There is no unique record from query.");
        });
    }

    public Record getRecord(String sql, Object ... params) {
        return get((rs, idx) -> new Record(rs), sql, params);
    }

    public List<Record> query(String sql, Object ... params) {
        final List<String> names = new ArrayList<>();
        return query((rs, idx) -> {
            if (idx == 0) {
                names.addAll(getFieldNames(rs));
            }
            return new Record(rs, names);
        }, sql, params);
    }

    public <T> List<T> query(RSMapper<T> mapper, String sql, Object ... params) {
        return withConn(conn -> {
            List<T> list = new ArrayList<>();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParams(stmt, params);
                stmt.executeQuery();
                try (ResultSet rs = stmt.getResultSet()) {
                    int idx = 0;
                    while (rs.next()) {
                        T t = mapper.map(rs, idx++);
                        list.add(t);
                    }
                }
            }
            return list;
        });
    }

    public int execute(String sql, Object ... params) {
        return withConn(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setParams(stmt, params);
                return stmt.executeUpdate();
            }
        });
    }

    public int insert(String table, Record record) {
        return insert(table, record, record.getFieldNames());
    }

    public int insert(String table, Record record, List<String> insertFields) {
        String fields = insertFields.stream().collect(Collectors.joining(", "));
        String qMarks = insertFields.stream().map(e -> "?").collect(Collectors.joining(", "));
        String sql = "INSERT INTO " + table + "(" + fields + ") VALUES (" + qMarks + ")";
        List<Object> params = insertFields.stream().map(e -> record.get(e)).collect(Collectors.toList());
        return withConn(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setParams(stmt, params.toArray());
                int uc = stmt.executeUpdate();
                // Check for auto generated keys, if present add into the record.
                // NOTE it will not override existing fields in record!
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        List<String> names = getFieldNames(rs);
                        for (String name : names) {
                            if (!record.containsKey(name))
                                record.put(name, rs.getObject(name));
                        }
                    }
                }
                return uc;
            }
        });
    }

    public int update(String table, Record record, String ... keyFields) {
        List<String> updateFields = record.getFieldNames();
        for (String key : keyFields)
            updateFields.remove(key);
        return update(table, record, updateFields, Arrays.asList(keyFields));
    }

    public int update(String table, Record record, List<String> updateFields, List<String> keyFields) {
        String updateFieldsNames = updateFields.stream().map(e -> e + " = ?").collect(Collectors.joining(", "));
        String keyFieldsNames = keyFields.stream().map(e -> e + " = ?").collect(Collectors.joining(", "));
        String sql = "UPDATE " + table + " SET " + updateFieldsNames + " WHERE " + keyFieldsNames;
        List<Object> params = updateFields.stream().map(e -> record.get(e)).collect(Collectors.toList());
        for (String key : keyFields)
            params.add(record.get(key));
        return execute(sql, params.toArray());
    }

    // Test Jdbc
    public static void main(String[] args) {
        Properties props = System.getProperties();
        props.setProperty("url", props.getProperty("url", "jdbc:mysql://localhost:3306/mysql"));
        props.setProperty("user", props.getProperty("user", "root"));
        props.setProperty("password", props.getProperty("password", ""));
        props.setProperty("sql", props.getProperty("sql", "SELECT VERSION()"));
        Jdbc.withJdbc(props, jdbc -> {
            String sql = props.getProperty("sql");
            List<Record> list = jdbc.query(sql);
            for (Record rec : list) {
                System.out.println(rec);
            }
            System.out.println("Total " + list.size() + " records found.");
        });
    }
}
