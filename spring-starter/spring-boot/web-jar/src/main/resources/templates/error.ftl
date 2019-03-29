<!DOCTYPE html>
<html>
<#include "/includes/html-head.ftl">
<#include "/includes/header.ftl">

<div class="container">
    <div class="main-content">
        <h1>${error}</h1>
        <div class="alert alert-danger" role="alert">
            <h2>${status} ${path}</h2>
            <p>${message}</p>
        </div>
    </div>
    <#include "/includes/footer.ftl">
</div>

<#include "/includes/html-tail.ftl">
</html>
