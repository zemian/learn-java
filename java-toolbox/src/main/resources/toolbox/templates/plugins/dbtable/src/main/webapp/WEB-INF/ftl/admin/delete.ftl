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
        <h1>Delete ${domain.className}</h1>

        <p>Are you sure you want to delete ${keyField.nameLabel} <#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}?</p>

        <form id="setting" class="form-horizontal" method="post" action="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/delete">
            <input type="hidden" id="${keyField.name}" name="${keyField.name}" value="<#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}">
            <div class="form-group">
                <div class="col-sm-12">
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