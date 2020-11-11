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
        <h1>${domain.className} Detail</h1>

        <p>
            <a href="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/list"><span title="List" class="glyphicon glyphicon-list" aria-hidden="true"></span> List</a>
            <a href="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/edit/<#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}"><span title="Edit" class="glyphicon glyphicon-edit" aria-hidden="true"></span> Edit</a>
            <a href="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/delete/<#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}"><span title="Delete" class="glyphicon glyphicon-remove" aria-hidden="true"></span> Delete</a>
        </p>

        <table class="table">
            <tr>
                <td>${keyField.nameLabel}</td><td><#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}</td>
            </tr>
            <#list domain.nonKeyFields as field>
            <tr>
                <td>${field.nameLabel}</td><td><#noparse>$</#noparse>{${domain.classVar}.${field.name}!''}</td>
            </tr>
            </#list>
        </table>
    </div>
<#noparse><#include "/includes/footer.ftl">
</div>

<#include "/includes/html-tail.ftl">
</body>
</html>
</#noparse>