<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="main-content">
        <h1>Error: ${requestScope['javax.servlet.error.status_code']}</h1>
        <div class="alert alert-danger" role="alert">
            <h2>${requestScope['javax.servlet.error.request_uri']}</h2>
            <pre>${requestScope['javax.servlet.error.exception'].message}</pre>
        </div>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</html>
