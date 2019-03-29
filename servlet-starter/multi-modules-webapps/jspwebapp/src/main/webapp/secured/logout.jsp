<%
    if (request.getSession(false) != null) {
        String username = request.getUserPrincipal().getName();
        request.setAttribute("logoutUsername", username);
        request.logout();
        // The request.logout() will invalidate session already.
        //session.invalidate();
    }
%>
<!DOCTYPE html>
<html>
<%@include file="../includes/html-head.jsp"%>
<%@include file="../includes/header.jsp"%>

<div class="container">
    <div class="main-content">
        <p>User ${logoutUsername} has been logged out.</p>
    </div>
    <%@include file="../includes/footer.jsp"%>
</div>

<%@include file="../includes/html-tail.jsp"%>
</html>