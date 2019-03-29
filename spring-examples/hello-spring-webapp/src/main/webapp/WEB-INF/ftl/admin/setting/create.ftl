<!DOCTYPE html>
<html>
<head>
<#include "/includes/html-head.ftl">
</head>
<body>
<#include "/includes/header.ftl">
<div class="container">
    <div class="app-content">
        <h1>Create New Setting</h1>

        <#include "/includes/form-errors.ftl">

        <form id="setting" class="form-horizontal" method="post" action="${app.contextPath}/admin/setting/create">
            <#include "/admin/setting/form-body.ftl">
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Submit</button>
                </div>
            </div>
        </form>
    </div>
<#include "/includes/footer.ftl">
</div>

<#include "/includes/html-tail.ftl">
</body>
</html>
