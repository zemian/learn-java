<%@ page import="com.zemian.hellojava.AppManager" %>
<!DOCTYPE html>
<html>
<%@include file="includes/html-head.jsp"%>
<%@include file="includes/header.jsp"%>

<div class="container">
    <div class="main-content">
        <%
            // This is just a test page for app health
            AppManager app = AppManager.getInstance();
            out.println("This application has been up since " + app.getStartupTime());
        %>
    </div>
    <%@include file="includes/footer.jsp"%>
</div>

<%@include file="includes/html-tail.jsp"%>
</html>