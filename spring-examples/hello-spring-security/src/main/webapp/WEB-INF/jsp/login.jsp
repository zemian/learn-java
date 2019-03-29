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
        <div class="row">
            <div class="col-xs-4"></div>
            <div class="col-xs-4">
                <form class="form-horizontal" action="${app.contextPath}/login" method="post">
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="input" class="form-control" id="username" name="username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" class="form-control" id="password" name="password">
                    </div>
                    <button class="btn btn-success">Submit</button>
                </form>
            </div>
            <div class="col-xs-4"></div>
        </div>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>
