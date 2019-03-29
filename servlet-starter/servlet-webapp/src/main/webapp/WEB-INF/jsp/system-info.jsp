<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="main-content">
        <h1>System Information</h1>
        <table class="table">
            <c:forEach var="entry" items="${sysInfo}">
                <tr><td>${entry.key}</td><td>${entry.value}</td></tr>
            </c:forEach>
        </table>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</html>