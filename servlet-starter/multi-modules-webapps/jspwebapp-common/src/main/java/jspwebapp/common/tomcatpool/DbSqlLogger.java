package jspwebapp.common.tomcatpool;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.apache.tomcat.jdbc.pool.interceptor.AbstractQueryReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Tomcat JDBC pool interceptor that logs any SQL going through the pool.
 */
public class DbSqlLogger extends AbstractQueryReport {
    private static final Logger LOG = LoggerFactory.getLogger(DbSqlLogger.class);

    private String poolName;

    @Override
    public void reset(ConnectionPool pool, PooledConnection conn) {
        // Capture the poolName used for logging.
        super.reset(pool, conn);
        poolName = (pool != null) ? pool.getName() : "NULL";

        // This force parent class to not to consider any slow queries
        // We onlyl interested in logging the SQL, not respond to it's speed.
        threshold = Long.MAX_VALUE;
    }


    @Override
    protected void prepareStatement(String sql, long time) {
        // Do nothing.
    }

    @Override
    protected void prepareCall(String query, long time) {
        // Do nothing.
    }

    @Override
    public void closeInvoked() {
        // Do nothing.
    }

    @Override
    protected String reportQuery(String query, Object[] args, String name, long start, long delta) {
        String sql = super.reportQuery(query, args, name, start, delta);
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL + (pool=" + poolName + ", time=" + delta + "): " + sql);
        }
        return sql;
    }

    @Override
    protected String reportSlowQuery(String query, Object[] args, String name, long start, long delta) {
        String sql = super.reportSlowQuery(query, args, name, start, delta);
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL + (pool=" + poolName + ", time=" + delta + "): " + sql);
        }
        return sql;
    }
}
