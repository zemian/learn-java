package jspwebapp.common.tomcatpool;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.JdbcInterceptor;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.apache.tomcat.jdbc.pool.interceptor.AbstractCreateStatementInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * A Tomcat JDBC pool interceptor that logs any SQL and it's parameters going through the pool.
 */
public class DbSlowQuerySimulator extends AbstractCreateStatementInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(DbSlowQuerySimulator.class);

    private String poolName;
    private long delayTimeInMillis;

    @Override
    public void setProperties(Map<String, PoolProperties.InterceptorProperty> properties) {
        super.setProperties(properties);
        this.delayTimeInMillis = properties.get("delayTimeInMillis").getValueAsLong(0);
    }

    @Override
    public void reset(ConnectionPool pool, PooledConnection conn) {
        super.reset(pool, conn);
        poolName = (pool != null) ? pool.getName() : "NULL";
    }

    @Override
    public void closeInvoked() {
        // Do nothing.
    }

    @Override
    public Object createStatement(Object proxy, Method method, Object[] args, Object statement, long time) {
        try {
            Object result = null;
            String name = method.getName();
            String sql = null;
            Constructor<?> constructor = null;
            if (compare(CREATE_STATEMENT,name)) {
                //createStatement
                constructor = getConstructor(CREATE_STATEMENT_IDX,Statement.class);
            }else if (compare(PREPARE_STATEMENT,name)) {
                //prepareStatement
                sql = (String)args[0];
                constructor = getConstructor(PREPARE_STATEMENT_IDX,PreparedStatement.class);
            }else if (compare(PREPARE_CALL,name)) {
                //prepareCall
                sql = (String)args[0];
                constructor = getConstructor(PREPARE_CALL_IDX,CallableStatement.class);
            }else {
                //do nothing, might be a future unsupported method
                //so we better bail out and let the system continue
                return statement;
            }
            result = constructor.newInstance(new Object[] { new StatementProxy(statement,sql) });
            return result;
        }catch (Exception x) {
            LOG.warn("Unable to create statement proxy for slow query report.",x);
        }
        return statement;
    }

    protected class StatementProxy implements InvocationHandler {
        protected boolean closed = false;
        protected Object delegate;
        protected final String query;
        public StatementProxy(Object parent, String query) {
            this.delegate = parent;
            this.query = query;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //get the name of the method for comparison
            final String name = method.getName();
            //was close invoked?
            boolean close = compare(JdbcInterceptor.CLOSE_VAL,name);
            //allow close to be called multiple times
            if (close && closed) return null;
            //are we calling isClosed?
            if (compare(JdbcInterceptor.ISCLOSED_VAL,name)) return Boolean.valueOf(closed);
            //if we are calling anything else, bail out
            if (closed) throw new SQLException("Statement closed.");

            // Invoke the delegate object.
            long start = System.currentTimeMillis();
            Object result = null;
            try {
                result = method.invoke(delegate, args);
            } catch (Throwable t) {
                if (t instanceof InvocationTargetException
                        && t.getCause() != null) {
                    throw t.getCause();
                } else {
                    throw t;
                }
            }

            long delta = System.currentTimeMillis() - start;
            if (delayTimeInMillis > 0 && isExecute(method, false) && delta < delayTimeInMillis) {
                long delay = delayTimeInMillis - delta;
                LOG.info("Simulating DB delayTimeInMillis=" + delayTimeInMillis + ", delay=" + delay);
                Thread.sleep(delayTimeInMillis - delta);
                LOG.info("Simulating DB delayTimeInMillis=" + delayTimeInMillis + " is complete.");
            }

            //perform close cleanup
            if (close) {
                closed=true;
                delegate = null;
            }
            return result;
        }
    }
}
