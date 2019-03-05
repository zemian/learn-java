<%@ page import="java.sql.Connection" %>
<%@ page import="jspwebapp.common.JdbcUtils" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.DatabaseMetaData" %>
<!DOCTYPE html>
<html>
<pre>
<%
    // WARNING: Simulate DB Connection Leak!
    out.println("== Open DB Conn Test");
    DataSource ds = JdbcUtils.getDataSource(JdbcUtils.DATASOURCE_NAME);
    Connection conn = ds.getConnection();
    String sql = "SELECT NOW()";
    try (Statement stmt = conn.createStatement()) {
        try (ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                out.println("SQL Query: \"" + sql + "\" = " + rs.getObject(1));
            }
        }
    }
    out.println("We will leave this conn=" + conn + " open on purpose for testing.");
%>
</pre>
</html>
