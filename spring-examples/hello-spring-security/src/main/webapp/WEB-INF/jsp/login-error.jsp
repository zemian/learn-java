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
        <h1>Login Error</h1>
        <div class="alert alert-danger" role="alert">
            <p>Invalid user name or password!</p>
        </div>
    </div>
	
	<%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>
<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>