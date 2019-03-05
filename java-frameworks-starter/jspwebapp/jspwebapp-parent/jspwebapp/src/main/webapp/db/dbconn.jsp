<%@ page import="java.sql.Connection" %>
<%@ page import="jspwebapp.common.JdbcUtils" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.DatabaseMetaData" %>
<%@ page import="java.sql.PreparedStatement" %>
<!DOCTYPE html>
<html>
<pre>
<%
    out.println("== DB Information");
    DataSource ds = JdbcUtils.getDataSource(JdbcUtils.DATASOURCE_NAME);
    try (Connection conn = ds.getConnection()) {
        out.println("DATASOURCE_NAME = " + JdbcUtils.DATASOURCE_NAME);
        out.println("DataSource.class = " + ds.getClass().getName());
        out.println("Connection.class = " + conn.getClass().getName());
        out.println("Connection.identityHashCode = " + System.identityHashCode(conn));
        out.println("Connection Info = " + conn.toString());
        DatabaseMetaData dbMeta = conn.getMetaData();
        out.println("DB Name = " + dbMeta.getDatabaseProductName());
        out.println("DB Version = " + dbMeta.getDatabaseProductVersion());
        out.println("Driver = " + dbMeta.getDriverName());
        out.println("Driver Version = " + dbMeta.getDriverVersion());
        out.println("URL = " + dbMeta.getURL());
        out.println("UserName = " + dbMeta.getUserName());

        String testCase = request.getParameter("testCase");
        if ("1".equals(testCase)) {
            String sql = "SELECT NOW()";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        out.println("SQL Query: \"" + sql + "\" = " + rs.getObject(1));
                    }
                }
            }
        } else if ("2".equals(testCase)) {
            String sql = "SELECT 1 + ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, 99);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        out.println("SQL Query: \"" + sql + "\" = " + rs.getObject(1));
                    }
                }
            }
        } else {
            String sql = "SELECT NOW()";
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs.next()) {
                        out.println("SQL Query: \"" + sql + "\" = " + rs.getObject(1));
                    }
                }
            }
        }
    }
%>
</pre>
</html>
