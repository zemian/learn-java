<!DOCTYPE html>
<html>
<head>
<#include "/includes/html-head.ftl">
</head>
<body>
<#include "/includes/header.ftl">

<div class="container">
    <div class="app-content">
        <h1>System Information</h1>
        <table class="table">
            <#list sysInfo as key, value>
                <tr><td>${key}</td><td>${value}</td></tr>
            </#list>
        </table>
    </div>
    <#include "/includes/footer.ftl">
</div>

<#include "/includes/html-tail.ftl">
</body>
</html>
