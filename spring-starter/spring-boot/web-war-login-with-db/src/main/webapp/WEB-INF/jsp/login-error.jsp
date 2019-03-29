<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="includes/html-head.jsp"%>
<%@include file="includes/header.jsp"%>

<div class="container">
    <div class="main-content">	
        <h1>Login Error</h1>
        <div class="alert alert-danger" role="alert">
            <p>Invalid user name or password!</p>
        </div>
    </div>
	
	<%@include file="includes/footer.jsp"%>
</div>
<%@include file="includes/html-tail.jsp"%>
</html>