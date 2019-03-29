package zemian.hellojava.db;

import zemian.hellojava.Opts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * A simple DB connection utility to provide a Connection object to be consume
 * by any task.
 *
 * You may also use this as command line program to run a simple query for connection
 * verification purpose. Default connection info are:
 *   --url=jdbc:postgresql://localhost:5432/postgres
 *   --user=postgres
 *   --password=
 * You may change these as command line options and you may add additional DB properties.
 */
public class DBConn {
    public interface ConnConsumer {
        void apply(Connection conn) throws Exception;
    }

    public static void withConn(ConnConsumer work) {
        withConn(new Properties(), work);
    }

    public static void withConn(Properties props, ConnConsumer work) {
        String url = props.getProperty("url", "jdbc:postgresql://localhost:5432/postgres");
        if (!props.containsKey("user"))
            props.put("user", "postgres");
        try {
            try (Connection conn = DriverManager.getConnection(url, props)) {
                work.apply(conn);
            }
        } catch (RuntimeException e) {
            throw e; // Just rethrow it.
        } catch (Exception e) {
            throw new RuntimeException("DB conn failed", e); // Wraps it as uncheck
        }
    }

    public static void main(String[] args) {
        Opts opts = new Opts(args);
        var query = opts.getOpt("query", "SELECT version()");
        Properties props = new Properties();
        props.putAll(opts.getOpts());
        DBConn.withConn(props, (conn) -> {
            System.out.println("Got: " + conn);

            System.out.println("Query: " + query);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            int colSize = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= colSize; i++) {
                    row.add("" + rs.getObject(i));
                }
                System.out.println("\t" + String.join(", ", row));
            }
            st.close();
        });
    }
}
