<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/jsp/includes/html-head.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/jsp/includes/header.jsp"%>

<div class="container">
    <div class="app-content">

<h1>Vars</h1>
<pre>
    sysProps=${sysProps}
    sysEnv=${sysEnv}
    currentDirFiles=${currentDirFiles}
    currentTimeMillis=${currentTimeMillis}
    currentDate=${currentDate}
    currentTimeMillis=${currentTimeMillis}
    currentDate=${currentDate}
    java8LocalDateTime=${java8LocalDateTime}
</pre>

<h1>JSP Scope Vars</h1>
<pre>
    pageScope=${pageScope}
    requestScope=${requestScope}
    sessionScope=${sessionScope}
    applicationScope=${applicationScope}
</pre>

    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>
