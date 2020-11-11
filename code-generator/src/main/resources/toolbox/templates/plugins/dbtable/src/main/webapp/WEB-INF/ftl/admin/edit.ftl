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
        <h1>Edit ${domain.className}</h1>

        <#noparse><#include</#noparse> "/includes/form-errors.ftl">

        <form id="${domain.classVar}" class="form-horizontal" method="post" action="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/edit">
            <input type="hidden" id="${keyField.name}" name="${keyField.name}" value="<#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}">
            <div class="form-group">
                <label for="id" class="col-sm-2 control-label">${keyField.nameLabel}</label>
                <div id="id" class="col-sm-10">
                    <p class="form-control-static"><#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}</p>
                </div>
            </div>
            <#noparse><#include</#noparse> "${controller.webUiUrlMapping}/${domain.classNameUrl}/form-body.ftl">
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Submit</button>
                </div>
            </div>
        </form>
    </div>
<#noparse><#include "/includes/footer.ftl">
</div>

<#include "/includes/html-tail.ftl">
</body>
</html>
</#noparse>