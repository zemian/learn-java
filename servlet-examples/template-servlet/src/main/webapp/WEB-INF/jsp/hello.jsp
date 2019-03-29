<%@ page import="com.zemian.hellojava.web.AppManager" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="main-content">
        <%
            // This is just a test page for app health
            AppManager app = AppManager.getInstance();
            out.println("This application has been up since " + app.getStartupTime());
        %>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</html>