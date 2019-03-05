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
        <h1>${domain.className} List View</h1>

        <#noparse><#if message??>
            <p class="alert alert-success">${message}</p>
        </#if></#noparse>

        <p>
            <a href="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/create"><span title="Create" class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create</a>
        </p>

        <table class="table">
            <thead>
                <th>${keyField.nameLabel}</th>
            <#list domain.nonKeyFields as field>
                <th>${field.nameLabel}</th>
            </#list>
                <th>Actions</th>
            </thead>
            <#noparse><#list</#noparse> plist.list as ${domain.classVar}>
                <tr>
                    <td><#noparse>$</#noparse>{${domain.classVar}.${keyField.name}!''}</td>
                    <#list domain.nonKeyFields as field>
                    <td><#noparse>$</#noparse>{${domain.classVar}.${field.name}!''}</td>
                    </#list>
                    <td>
                        <a href="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/detail/<#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}"><span title="Detail" class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span> View</a>
                        <a href="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/edit/<#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}"><span title="Edit" class="glyphicon glyphicon-edit" aria-hidden="true"></span> Edit</a>
                        <a href="<#noparse>$</#noparse>{app.contextPath}${controller.webUiUrlMapping}/${domain.classNameUrl}/delete/<#noparse>$</#noparse>{${domain.classVar}.${keyField.name}}"><span title="Delete" class="glyphicon glyphicon-remove" aria-hidden="true"></span> Delete</a>
                    </td>
                </tr>
            <#noparse></#list></#noparse>
        </table>
        <#noparse>
        <!-- Pagination -->
        <ul class="pager">
            <#if plist.paging.offset gt 0 && plist.prevPageOffset gte 0><li><a href="?offset=${plist.prevPageOffset}">Previous Page</a></li></#if>
            <#if plist.more><li><a href="?offset=${plist.nextPageOffset}">Next Page</a></li></#if>
        </ul>
        </#noparse>
    </div>
    <#noparse><#include "/includes/footer.ftl"></#noparse>
</div>
<#noparse>
<#include "/includes/html-tail.ftl">
</body>
</html>
</#noparse>