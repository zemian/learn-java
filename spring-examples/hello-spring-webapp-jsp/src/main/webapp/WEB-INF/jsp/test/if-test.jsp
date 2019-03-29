<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="app-content">

<h1>Test Condition</h1>
<pre>
    empty sessionScope=<c:if test="${empty sessionScope}">TRUE</c:if>
    not empty sessionScope=<c:if test="${not empty sessionScope}">TRUE</c:if>

    empty sessionScope['userSession']=<c:if test="${empty sessionScope['userSession']}">TRUE</c:if>
    not empty sessionScope['userSession']=<c:if test="${not empty sessionScope['userSession']}">TRUE</c:if>

    empty sessionScope['userSession'].user=<c:if test="${empty sessionScope['userSession'].user}">TRUE</c:if>
    not empty sessionScope['userSession'].user=<c:if test="${not empty sessionScope['userSession'].user}">TRUE</c:if>
</pre>


    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>
