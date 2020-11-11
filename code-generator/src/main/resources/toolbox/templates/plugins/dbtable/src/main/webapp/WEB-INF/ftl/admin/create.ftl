<#assign keyField = domain.keyFields?first>
<#noparse><!DOCTYPE html>
<html>
<head>
<#include "/includes/html-head.ftl">
</head>
<body>
<#include "/includes/header.ftl">
</#noparse>
<div class="container">
    <div class="app-content">
        <h1>Create New ${domain.className}</h1>

        <#noparse><#include "/includes/form-errors.ftl"></#noparse>

        <form id="${domain.classVar}" class="form-horizontal" method="post" action="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/create">
            <#if !(keyField.dbField.autoincrement)>
            <div class="form-group">
                <label for="id" class="col-sm-2 control-label">${keyField.nameLabel}</label>
                <div id="id" class="col-sm-10">
                    <input type="text" class="form-control" id="${keyField.name}" name="${keyField.name}" value="<#noparse>$</#noparse>{${domain.classVar}.${keyField.name}!''}">
                </div>
            </div>
            </#if>
            <#noparse><#include</#noparse> "${controller.webUiUrlMapping}/${domain.classNameUrl}/form-body.ftl">
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Submit</button>
                </div>
            </div>
        </form>
    </div>
<#noparse>
<#include "/includes/footer.ftl">
</div>

<#include "/includes/html-tail.ftl">
</body>
</html>
</#noparse>