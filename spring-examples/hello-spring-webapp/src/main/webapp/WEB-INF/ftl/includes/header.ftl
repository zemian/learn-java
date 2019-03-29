<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${app.contextPath}/">${app.config['app.web.name']}</a>
        </div>

        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <#if userSession?? && userSession.user??>
                    <#if userSession.user.admin>
                        <li id="user"><a href="${app.contextPath}/admin/user/list">Users</a></li>
                        <li id="setting"><a href="${app.contextPath}/admin/setting/list">Settings</a></li>
                        <li id="auditLog"><a href="${app.contextPath}/admin/audit-log/list">AuditLogs</a></li>
                    </#if>
                    <li id="system-info"><a href="${app.contextPath}/admin/system-info">SystemInfo</a></li>
                <#else>
                    <li id="about"><a href="${app.contextPath}/admin">Admin</a></li>
                </#if>
                <li id="about"><a href="${app.contextPath}/about">About</a></li>
            </ul>
            <#if userSession?? && userSession.user??>
                <ul class="nav navbar-nav navbar-right">
                    <p class="navbar-text"><strong>User: ${userSession.user.username}</strong></p>
                    <li><a href="${app.contextPath}/logout">Logout</a></li>
                </ul>
            </#if>
        </div>
    </div>
</nav>