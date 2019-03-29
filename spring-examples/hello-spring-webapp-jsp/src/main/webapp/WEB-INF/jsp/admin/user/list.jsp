<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="app-content">
        <h1>Users</h1>
        <p><a href="${app.contextPath}/admin/user/create">Create New User</a></p>
        <table class="table">
            <tr>
                <td>Username</td>
                <td>Full Name</td>
                <td>Admin</td>
            </tr>
            <c:forEach var="user" items="${users.list}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.fullName}</td>
                    <td>${user.admin}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>