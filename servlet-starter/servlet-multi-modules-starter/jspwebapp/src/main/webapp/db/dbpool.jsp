<%@ page import="jspwebapp.common.JdbcUtils" %>
<%@ page import="jspwebapp.common.GlobalData" %>
<%@ page import="java.util.Set" %>
<%@ page import="jspwebapp.common.tomcatpool.DbConnLogger" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<pre>
<%
    out.println("== Today's Date: " + new Date());

    // Out custom Tomcat JDBC Pool Interceptor Data Counts
    GlobalData gd = GlobalData.getInstance();
    out.println("== Global Data For JDBC Pool");
    Set<String> names = gd.getNames();
    for (String name : names) {
        if (name.indexOf(DbConnLogger.GD_BORROWED_COUNT) >= 0
                || name.indexOf(DbConnLogger.GD_RETURNED_COUNT) >= 0) {
            out.println(name + " = " + gd.getData(name));
        }
    }
%>

<%
    request.setAttribute("dbPoolInfo", JdbcUtils.getDataSource(JdbcUtils.DATASOURCE_NAME));
%>
== DB Pool Summary
numActive = ${dbPoolInfo.numActive}
numIdle = ${dbPoolInfo.numIdle}
borrowedCount = ${dbPoolInfo.borrowedCount}
createdCount = ${dbPoolInfo.createdCount}
reconnectedCount = ${dbPoolInfo.reconnectedCount}
releasedCount = ${dbPoolInfo.releasedCount}
releasedIdleCount = ${dbPoolInfo.releasedIdleCount}
removeAbandoned = ${dbPoolInfo.removeAbandoned}
removeAbandonedCount = ${dbPoolInfo.removeAbandonedCount}
returnedCount = ${dbPoolInfo.returnedCount}
waitCount = ${dbPoolInfo.waitCount}

== DB Pool Details
abandonWhenPercentageFull = ${dbPoolInfo.abandonWhenPercentageFull}
accessToUnderlyingConnectionAllowed = ${dbPoolInfo.accessToUnderlyingConnectionAllowed}
active = ${dbPoolInfo.active}
alternateUsernameAllowed = ${dbPoolInfo.alternateUsernameAllowed}
borrowedCount = ${dbPoolInfo.borrowedCount}
connectionProperties = ${dbPoolInfo.connectionProperties}
createdCount = ${dbPoolInfo.createdCount}
dataSource = ${dbPoolInfo.dataSource}
dataSourceJNDI = ${dbPoolInfo.dataSourceJNDI}
defaultAutoCommit = ${dbPoolInfo.defaultAutoCommit}
defaultCatalog = ${dbPoolInfo.defaultCatalog}
defaultReadOnly = ${dbPoolInfo.defaultReadOnly}
defaultTransactionIsolation = ${dbPoolInfo.defaultTransactionIsolation}
driverClassName = ${dbPoolInfo.driverClassName}
fairQueue = ${dbPoolInfo.fairQueue}
idle = ${dbPoolInfo.idle}
ignoreExceptionOnPreLoad = ${dbPoolInfo.ignoreExceptionOnPreLoad}
initSQL = ${dbPoolInfo.initSQL}
initialSize = ${dbPoolInfo.initialSize}
jdbcInterceptors = ${dbPoolInfo.jdbcInterceptors}
jmxEnabled = ${dbPoolInfo.jmxEnabled}
logAbandoned = ${dbPoolInfo.logAbandoned}
logValidationErrors = ${dbPoolInfo.logValidationErrors}
loginTimeout = ${dbPoolInfo.loginTimeout}
maxActive = ${dbPoolInfo.maxActive}
maxAge = ${dbPoolInfo.maxAge}
maxIdle = ${dbPoolInfo.maxIdle}
maxWait = ${dbPoolInfo.maxWait}
minEvictableIdleTimeMillis = ${dbPoolInfo.minEvictableIdleTimeMillis}
minIdle = ${dbPoolInfo.minIdle}
name = ${dbPoolInfo.name}
numActive = ${dbPoolInfo.numActive}
numIdle = ${dbPoolInfo.numIdle}
numTestsPerEvictionRun = ${dbPoolInfo.numTestsPerEvictionRun}
poolSweeperEnabled = ${dbPoolInfo.poolSweeperEnabled}
propagateInterruptState = ${dbPoolInfo.propagateInterruptState}
reconnectedCount = ${dbPoolInfo.reconnectedCount}
releasedCount = ${dbPoolInfo.releasedCount}
releasedIdleCount = ${dbPoolInfo.releasedIdleCount}
removeAbandoned = ${dbPoolInfo.removeAbandoned}
removeAbandonedCount = ${dbPoolInfo.removeAbandonedCount}
removeAbandonedTimeout = ${dbPoolInfo.removeAbandonedTimeout}
returnedCount = ${dbPoolInfo.returnedCount}
rollbackOnReturn = ${dbPoolInfo.rollbackOnReturn}
size = ${dbPoolInfo.size}
suspectTimeout = ${dbPoolInfo.suspectTimeout}
testOnBorrow = ${dbPoolInfo.testOnBorrow}
testOnConnect = ${dbPoolInfo.testOnConnect}
testWhileIdle = ${dbPoolInfo.testWhileIdle}
timeBetweenEvictionRunsMillis = ${dbPoolInfo.timeBetweenEvictionRunsMillis}
url = ${dbPoolInfo.url}
useDisposableConnectionFacade = ${dbPoolInfo.useDisposableConnectionFacade}
useEquals = ${dbPoolInfo.useEquals}
useLock = ${dbPoolInfo.useLock}
useStatementFacade = ${dbPoolInfo.useStatementFacade}
username = ${dbPoolInfo.username}
validationInterval = ${dbPoolInfo.validationInterval}
validationQuery = ${dbPoolInfo.validationQuery}
validationQueryTimeout = ${dbPoolInfo.validationQueryTimeout}
validatorClassName = ${dbPoolInfo.validatorClassName}
waitCount = ${dbPoolInfo.waitCount}
</pre>
</html>
