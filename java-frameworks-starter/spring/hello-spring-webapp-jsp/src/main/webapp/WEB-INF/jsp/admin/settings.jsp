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
            <tr>
                <td>ID</td>
                <td>Category</td>
                <td>Name</td>
                <td>Value</td>
            </tr>
            <c:forEach var="setting" items="${settings.list}">
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