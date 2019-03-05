<%@ page import="java.util.Properties" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="includes/html-head.jsp"%>
<%@include file="includes/header.jsp"%>

<%
    request.setAttribute("sysInfo", System.getProperties());
%>

<div class="container">
    <div class="main-content">
        <h1>System Information</h1>
        <table class="table">
            <c:forEach var="entry" items="${sysInfo}">
                <tr><td>${entry.key}</td><td>${entry.value}</td></tr>
            </c:forEach>
        </table>
    </div>
    <%@include file="includes/footer.jsp"%>
</div>

<%@include file="includes/html-tail.jsp"%>
</html>