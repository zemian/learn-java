<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${app.contextPath}/">HelloApp</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <c:if test="${empty sessionScope['userSession'].user}">
                    <li id="about"><a href="${app.contextPath}/admin">Admin</a></li>
                </c:if>

                <c:if test="${not empty sessionScope['userSession'].user}">
                    <li id="users"><a href="${app.contextPath}/admin/user/list">Users</a></li>
                    <li id="settings"><a href="${app.contextPath}/admin/settings">Settings</a></li>
                    <li id="system-info"><a href="${app.contextPath}/admin/system-info">SystemInfo</a></li>
                </c:if>

                <li id="about"><a href="${app.contextPath}/about">About</a></li>
            </ul>
            <c:if test="${not empty sessionScope['userSession'].user}">
                <ul class="nav navbar-nav navbar-right">
                    <p class="navbar-text"><strong>User: ${sessionScope['userSession'].user.username}</strong></p>
                    <li><a href="${app.contextPath}/logout">Logout</a></li>
                </ul>
            </c:if>
        </div>
    </div>
</nav>