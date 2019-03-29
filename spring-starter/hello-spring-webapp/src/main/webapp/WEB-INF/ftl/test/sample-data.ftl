<!DOCTYPE html>
<html>
<head>
<#include "/includes/html-head.ftl">
</head>
<body>
<#include "/includes/header.ftl">

<div class="container">
    <div class="app-content">
        <h1>Sample Data</h1>

        <pre>Sample Data
            currentTimeMillis: ${currentTimeMillis}
            currentDate: ${currentDate?datetime}
            java8LocalDateTime: ${java8LocalDateTime}
        <#list currentDirFiles as item>
            File: ${item}
        </#list>
        <#list sysProps?keys as key>
            SysProps: ${key} = ${sysProps[key]}
        </#list>
        <#list sysEnv?keys as key>
            SysEnv: ${key} = ${sysEnv[key]}
        </#list>
        </pre>

        <pre>.data_model
        <#list .data_model?keys as key>
          ${key}
        </#list>
        </pre>

        <#--<pre>.globals-->
        <#--<#list .globals?keys as key>-->
        <#--${key}-->
        <#--</#list>-->
        <#--</pre>-->

        <#--<pre>.locals-->
        <#--<#list .locals?keys as key>-->
        <#--${key}-->
        <#--</#list>-->
        <#--</pre>-->
    </div>
    <#include "/includes/footer.ftl">
</div>

<#include "/includes/html-tail.ftl">
</body>
</html>
