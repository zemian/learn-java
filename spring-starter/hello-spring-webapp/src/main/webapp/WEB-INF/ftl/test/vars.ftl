<!DOCTYPE html>
<html>
<head>
<#include "/includes/html-head.ftl">
</head>
<body>
<#include "/includes/header.ftl">

<div class="container">
    <div class="app-content">
        <h1>Request Vars</h1>
        <pre>
        <#list Request?keys as key>
        ${key} = ${javaToString(Request[key])}
        </#list>
        </pre>

        <#-- NOTE: These Session and Application variables does not implements HashModelEx and not iterable! So
        they can only be used as explicit lookup but not listing values!

        <#if Session ??>
        <h1>Session Vars</h1>
        <pre>
        <#list Session?keys as key>
            ${key} = ${javaToString(Session[key])}
        </#list>
        </pre>
        </#if>

        <h1>Application Vars</h1>
        <pre>
        <#list Application?keys as key>
            ${key} = ${javaToString(Application[key])}
        </#list>
        </pre>

        <h1>RequestParameters Vars</h1>
        <pre>
        <#list RequestParameters?keys as key>
            ${key} = ${javaToString(RequestParameters[key])}
        </#list>
        </pre>

        <h1>JspTaglibs Vars</h1>
        <pre>
        <#list JspTaglibs?keys as key>
            ${key} = ${javaToString(JspTaglibs[key])}
        </#list>
        </pre>
        -->

        <#if sessionVarNames ??>
        <h1>Session Vars</h1>
        <pre>
        <#list sessionVarNames as key>
            ${key} = ${javaToString(Session[key])}
        </#list>
        </pre>
        </#if>

        <h1>Application Vars</h1>
        <pre>
        <#list applicationVarNames as key>
            ${key} = ${javaToString(Application[key])}
        </#list>
        </pre>
    </div>
    <#include "/includes/footer.ftl">
</div>

<#include "/includes/html-tail.ftl">
</body>
</html>
