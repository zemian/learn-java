package zemian.myava.jdbc.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zemian on 10/21/16.
 */
public class InsertSettingsSample {
    public static final Logger LOG = LoggerFactory.getLogger(InsertSettingsSample.class);

    private static final String INSERT_SETTINGS_SQL = "insert into settings(category, name, value) values (?, ?, ?)";

    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/test";
        String user = "test";
        String password = "test";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            LOG.debug("Using conn {}", conn);
            //insertSettingsSample(conn);
            insertSettingsSystemProps(conn);
            insertSettingsEnv(conn);
        }
        LOG.info("Done");
    }

    private static void insertSettingsSystemProps(Connection conn) throws Exception {
        try (PreparedStatement st = conn.prepareStatement(INSERT_SETTINGS_SQL)) {
            String cat = "SYS_PROPS";
            Properties props = System.getProperties();
            for (String name : props.stringPropertyNames()) {
                String value = props.getProperty(name);
                LOG.info("Inserting record: cat={}, name={}", cat, name);
                st.setString(1, cat);
                st.setString(2, name);
                st.setString(3, value);

                try {
                    int result = st.executeUpdate();
                    LOG.debug("{} record inserted.", result);
                } catch (SQLException e) {
                    //LOG.error("Failed to insert record.", e);
                    LOG.error("Failed to insert record: " + e.getMessage());
                }

                // Ready to do again
                st.clearParameters();
            }
        }
    }

    private static void insertSettingsEnv(Connection conn) throws Exception {
        String cat = "ENV";
        // Try to remove all cat entry first!
        try (PreparedStatement st = conn.prepareStatement("delete from settings where category=?")) {
            st.setString(1, cat);
            int result = st.executeUpdate();
            LOG.info("{} existing records removed.", result);
        }

        try (PreparedStatement st = conn.prepareStatement(INSERT_SETTINGS_SQL)) {
            Map<String, String> env = System.getenv();
            for (String name : env.keySet()) {
                String value = env.get(name);
                LOG.info("Inserting record: cat={}, name={}", cat, name);
                st.setString(1, cat);
                st.setString(2, name);
                st.setString(3, value);

                int result = st.executeUpdate();
                LOG.debug("{} record inserted.", result);

                // Ready to do again
                st.clearParameters();
            }
        }
    }

    private static void insertSettingsSample(Connection conn) throws Exception {
        try (PreparedStatement st = conn.prepareStatement(INSERT_SETTINGS_SQL)) {
            String cat = "TEST_" + System.currentTimeMillis();
            for (int i = 1; i < 10; i++) {
                String name = "test" + i;
                String value = "Just a test";
                LOG.info("Inserting record: cat={}, name={}", cat, name);
                st.setString(1, cat);
                st.setString(2, name);
                st.setString(3, value);

                int result = st.executeUpdate();
                LOG.debug("{} record inserted.", result);
            }
        }
    }
}
