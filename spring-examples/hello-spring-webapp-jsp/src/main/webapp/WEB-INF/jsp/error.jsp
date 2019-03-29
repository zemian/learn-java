<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="app-content">
        <h1>Oops! Something went wrong.</h1>
        <p>${requestScope['javax.servlet.error.status_code']} ${requestScope['javax.servlet.error.request_uri']}</p>
        <div class="alert alert-danger" role="alert">
            <p>${requestScope['javax.servlet.error.message']}</p>
        </div>

        <h3>Exception</h3>
        <pre>${exceptionStacktrace}</pre>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>
