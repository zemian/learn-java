<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="app-content">
        <h1>Test Page</h1>
        <ul>
            <li><a href="${app.contextPath}/test/view/vars">var scopes</a></li>
            <li><a href="${app.contextPath}/test/view/if-test">if tests</a></li>
        </ul>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>
