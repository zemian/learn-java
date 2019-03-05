<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="app-content">
        <form:form class="form-horizontal" modelAttribute="user" action="${app.contextPath}/admin/user/create" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <form:input class="form-control" path="username"/>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <form:password class="form-control" path="password"/>
            </div>
            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <form:input class="form-control" path="fullName"/>
            </div>
            <button class="btn btn-success" name="btnAction" value="create">Create</button>
        </form:form>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>