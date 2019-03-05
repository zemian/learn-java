<!--
NOTE: When testing this page, your browser might block parallels requests in multi TABS!!!
When this happens, it seems Tomcat is only processing one request at a time, but actually its
the client browser that blocking it!

Chrome 63.0.3239.132 multi tabs will able to test multiple requests!
Firefox 58.0.2 and IE version 11 do not work!
-->
<%@ page import="jspwebapp.common.JdbcUtils" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.time.Instant" %>
<!DOCTYPE html>
<html>
<pre>
<%
    long periodInMillis = 30 * 1000L;
    if (request.getParameter("periodInMillis") != null) {
        periodInMillis = Integer.parseInt(request.getParameter("periodInMillis"));
    }
    String threadName = Thread.currentThread().getName();

    out.println("== Simulate DB Work");
    out.println("Simulating " + periodInMillis + " seconds of work on thread: " + threadName + " ...");
    out.flush();
    DataSource ds = JdbcUtils.getDataSource(JdbcUtils.DATASOURCE_NAME);
    try (Connection conn = ds.getConnection()) {
        out.println("Got conn=" + System.identityHashCode(conn) + " " + conn);
        out.flush();

        // Do some actual DB work.
        long count = 0;
        long t1 = System.currentTimeMillis();
        long dataLength = 0;
        out.println("Start time = " + new Date(t1));
        out.println("Simulate periodInMillis = " + periodInMillis);

        //String sql = "SELECT NOW()";
        String sql = "select * from information_schema.tables";
        out.println("TEST Query: \"" + sql + "\"");

        while ((System.currentTimeMillis() - t1) < periodInMillis) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        int max = rs.getMetaData().getColumnCount();
                        for (int i = 1; i <= max; i++) {
                            Object result = rs.getObject(i);
                            if (result != null) {
                                dataLength += result.toString().length();
                            }
                        }
                    }
                }
                count++;
                out.println("Iteration#" + count + " dataLength = " + dataLength
                        + ", currentTime = " + new Date()
                        + ", elapse time = " + (System.currentTimeMillis() - t1));
                out.flush();
                Thread.sleep(1300L);
            }
        }

        out.println("Work is done.");
        out.println("End time = " + new Date());
        out.println("Request time = " + Duration.between(Instant.ofEpochMilli(t1), Instant.now()));
        out.flush();
    }
%>
</pre>
</html>
