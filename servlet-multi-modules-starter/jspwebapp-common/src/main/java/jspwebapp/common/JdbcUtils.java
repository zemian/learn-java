package jspwebapp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);

    public static final String DATASOURCE_NAME = "postgres_testDB";

    public static Connection getConnection() {
        return getConnection(DATASOURCE_NAME);
    }

    public static Connection getConnection(String jndiName) {
        try {
            Connection conn = getDataSource(jndiName).getConnection();
            LOG.debug("New DB connection created: jndiName={}, identityHashCode={}, conn={}"
                    ,jndiName, System.identityHashCode(conn), conn);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get DB Connection " + jndiName);
        }
    }

    public static DataSource getDataSource(String jndiName) {
        LOG.debug( "Getting JNDI datasource {}", jndiName );
        Context ctx = null;
        try {
            ctx = new InitialContext();
            DataSource result = (DataSource)((Context)ctx.lookup("java:comp/env")).lookup(jndiName);
            LOG.debug("Got DataSource: jndiName={}, impl={}", jndiName, result.getClass().getName());
            return result;
        } catch (NamingException e) {
            throw new RuntimeException("Failed to get JNDI datasource " + jndiName);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    throw new RuntimeException("Failed to close Context for JNDI datasource " + jndiName);
                }
            }
        }
    }

    public static class DBPoolInfo {
        private org.apache.tomcat.jdbc.pool.DataSource dataSource;

        public org.apache.tomcat.jdbc.pool.DataSource getDataSource() {
            return dataSource;
        }

        public void setDataSource(org.apache.tomcat.jdbc.pool.DataSource dataSource) {
            this.dataSource = dataSource;
        }
    }

    public static DBPoolInfo getDBPoolInfo() {
        DBPoolInfo ret = new DBPoolInfo();
        ret.setDataSource((org.apache.tomcat.jdbc.pool.DataSource)getDataSource(DATASOURCE_NAME));
        return ret;
    }
}
