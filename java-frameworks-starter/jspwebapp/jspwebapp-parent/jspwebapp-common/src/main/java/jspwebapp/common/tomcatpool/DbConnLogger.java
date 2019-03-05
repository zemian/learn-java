package jspwebapp.common.tomcatpool;

import jspwebapp.common.GlobalData;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.JdbcInterceptor;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A Tomcat JDBC pool interceptor that only log important information for debugging purpose.
 */
public class DbConnLogger extends JdbcInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(DbConnLogger.class);
    public static final String GD_BORROWED_COUNT = "DbConnLogger.borrowedCount";
    public static final String GD_RETURNED_COUNT = "DbConnLogger.returnedCount";


    protected PooledConnection conn = null;
    protected ConnectionPool pool = null;
    protected long timestamp;

    @Override
    public void reset(ConnectionPool pool, PooledConnection conn) {
        this.pool = pool;
        this.conn = conn;

        if (pool != null && conn != null) {
            String poolName = pool.getName();
            GlobalData gd = GlobalData.getInstance();

            String borrowedKey = poolName + "_" + GD_BORROWED_COUNT;
            AtomicInteger borrowedCount = gd.getData(borrowedKey);
            if (borrowedCount == null) {
                borrowedCount = new AtomicInteger(0);
                gd.putData(borrowedKey, borrowedCount);
            }

            String returnedKey = poolName + "_" + GD_RETURNED_COUNT;
            AtomicInteger returnedCount = gd.getData(returnedKey);
            if (returnedCount == null) {
                returnedCount = new AtomicInteger(0);
                gd.putData(returnedKey, returnedCount);
            }

            timestamp = System.currentTimeMillis();
            borrowedCount.incrementAndGet();

            // pool.size = busy + idle
            LOG.debug("DB connection=" + conn.getConnection() + ", borrowed from pool=" + poolName +
                    ", pool.numActive=" + pool.getActive() + ", pool.size=" + pool.getSize() +
                    ", borrowedCount=" + borrowedCount.get() + ", returnedCount=" + returnedCount.get());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (pool != null && conn != null) {
            if (compare(CLOSE_VAL, method)) {
                String poolName = pool.getName();
                GlobalData gd = GlobalData.getInstance();
                String borrowedKey = poolName + "_" + GD_BORROWED_COUNT;
                AtomicInteger borrowedCount = gd.getData(borrowedKey);
                String returnedKey = poolName + "_" + GD_RETURNED_COUNT;
                AtomicInteger returnedCount = gd.getData(returnedKey);
                long time = System.currentTimeMillis() - timestamp;

                returnedCount.incrementAndGet();
                LOG.debug("DB connection=" + conn.getConnection() + ", returned to pool=" + poolName +
                        ", pool.numActive=" + pool.getActive() + ", pool.size=" + pool.getSize() +
                        ", borrowedCount=" + borrowedCount.get() + ", returnedCount=" + returnedCount.get() +
                        ", time=" + time);
            }
        }
        return super.invoke(proxy, method, args);
    }
}
