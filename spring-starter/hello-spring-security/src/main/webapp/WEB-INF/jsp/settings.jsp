<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="app-content">
        <h1>Settings</h1>
        <table class="table">
            <c:forEach var="setting" items="${settings}">
                <tr>
                    <td>${setting.settingId}</td>
                    <td>${setting.category}</td>
                    <td>${setting.name}</td>
                    <td>${setting.value}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>