package zemian.myspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyDatabase {

    private final JdbcTemplate jdbc;
    private List<String> tables;

    @Autowired
    public MyDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }
	
	public String getVersion() {
		return (String)jdbc.execute((ConnectionCallback) connection -> {
            DatabaseMetaData meta = connection.getMetaData();
            String result = meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion();
            return result;
        });
	}

    public List<Table> listTables() {
        String sql = "SELECT table_name, table_type " +
                "FROM information_schema.tables " +
                "ORDER BY table_type, table_name";
        List<Table> result = jdbc.query(sql, (rs, i) -> {
            Table t = new Table();
            t.setName(rs.getString("table_name"));
            t.setType(rs.getString("table_type"));
            return t;
        });
        return result;
    }

    public static class Table {
        private String name;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
