<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${app.contextPath}/">HelloApp</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li id="settings"><a href="${app.contextPath}/settings">Settings</a></li>
                <li id="system-info"><a href="${app.contextPath}/system-info">SystemInfo</a></li>
                <li id="about"><a href="${app.contextPath}/about">About</a></li>
            </ul>
            <c:if test="${not empty pageContext.request.userPrincipal}">
            <ul class="nav navbar-nav navbar-right">
                <p class="navbar-text"><strong>User: ${pageContext.request.userPrincipal.name}</strong></p>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
            </c:if>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>