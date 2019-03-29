<!DOCTYPE html>
<html>
<%@include file="/includes/html-head.jsp"%>
<%@include file="/includes/header.jsp"%>

<div class="container">
    <div class="main-content">
        <div class="row">
            <div class="col-xs-4"></div>
            <div class="col-xs-4">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/j_security_check" method="post">
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="input" class="form-control" id="username" name="j_username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" class="form-control" id="password" name="j_password">
                    </div>
                    <button class="btn btn-success">Submit</button>
                </form>
            </div>
            <div class="col-xs-4"></div>
        </div>
    </div>
    <%@include file="/includes/footer.jsp"%>
</div>

<%@include file="/includes/html-tail.jsp"%>
</html>
