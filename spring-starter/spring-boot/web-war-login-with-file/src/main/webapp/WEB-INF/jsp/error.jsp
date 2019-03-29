<!DOCTYPE html>
<html>
<%@include file="includes/html-head.jsp"%>
<%@include file="includes/header.jsp"%>

<div class="container">
    <div class="main-content">
        <h1>${error}</h1>
        <div class="alert alert-danger" role="alert">
            <h2>${status} ${path}</h2>
            <p>${message}</p>
        </div>
    </div>
    <%@include file="includes/footer.jsp"%>
</div>

<%@include file="includes/html-tail.jsp"%>
</html>
