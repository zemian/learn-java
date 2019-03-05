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
        <form:form class="form-horizontal" modelAttribute="myForm" action="${app.contextPath}/my-form" method="post">
            <div class="form-group">
                <label for="path" class="col-sm-2 control-label">Path: </label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="path"/>
                </div>
            </div>
            <div class="form-group">
                <label for="subForm.name" class="col-sm-2 control-label">SubForm Name:</label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="subForm.name"/>
                </div>
            </div>
            <div class="form-group">
                <label for="subForm.value" class="col-sm-2 control-label">SubForm Value:</label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="subForm.value"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-success" name="btnAction" value="create">Submit</button>
                </div>
            </div>
        </form:form>
    </div>
    <%@include file="/WEB-INF/jsp/includes/footer.jsp"%>
</div>

<%@include file="/WEB-INF/jsp/includes/html-tail.jsp"%>
</body>
</html>