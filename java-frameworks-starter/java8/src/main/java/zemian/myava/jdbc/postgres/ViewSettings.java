package zemian.myava.jdbc.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by zemian on 10/21/16.
 */
public class ViewSettings {
    public static final Logger LOG = LoggerFactory.getLogger(ViewSettings.class);

    private static final String SELECT_SETTINGS_SQL = "select name, value from settings where category = ? order by name";

    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/test";
        String user = "test";
        String password = "test";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            LOG.debug("Using conn {}", conn);
            //printSettingsCat(conn);
            printSettings("ENV", conn);
        }
        LOG.info("Done");
    }

    private static void printSettingsCat(Connection conn) throws Exception {
        try (Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select category from settings group by category order by category")) {
                while (rs.next()) {
                    String cat = rs.getString("category");
                    System.out.println("Settings category: " + cat);
                }
            }
        }
    }

    private static void printSettings(String cat, Connection conn) throws Exception {
        try (PreparedStatement st = conn.prepareStatement(SELECT_SETTINGS_SQL)) {
            st.setString(1, cat);
            LOG.info("Query settings table with cat={}", cat);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String value = rs.getString("value");
                    System.out.println("Settings: " + name + "=" + value);
                }
            }
        }
    }
}
