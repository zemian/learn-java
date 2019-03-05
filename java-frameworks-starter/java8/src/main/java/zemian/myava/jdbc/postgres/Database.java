package zemian.myava.jdbc.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * Created by xbbj5x6 on 11/1/2016.
 */
public class Database {
    public static final Logger LOG = LoggerFactory.getLogger(Database.class);

    public static void withConnection(Consumer<Connection> consumer) throws Exception {
        withConnection(new Properties(), consumer);
    }
    public static void withConnection(Properties props, Consumer<Connection> consumer) throws Exception {
        String url = props.getProperty("dbUrl", "jdbc:postgresql://localhost:5432/test");
        String user = props.getProperty("dbUser", "test");
        String password = props.getProperty("dbPassword", "test");
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            LOG.debug("Using conn {}", conn);
            consumer.accept(conn);
        }
    }
}
