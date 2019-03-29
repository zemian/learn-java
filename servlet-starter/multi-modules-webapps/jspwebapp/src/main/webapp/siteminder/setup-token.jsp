<%@ page import="jspwebapp.common.siteminder.SiteMinderToken" %>
<%@ page import="jspwebapp.common.siteminder.SiteMinderTestValve" %>
<!DOCTYPE html>
<html>

<div class="container">
    <div class="main-content">
        <h1>Setup SiteMinder Auth Token For Testing</h1>
        <%
            SiteMinderToken token = (SiteMinderToken) session.getAttribute(SiteMinderTestValve.SESSION_TOKEN_KEY);

            if (token != null && request.getParameter("removeToken") != null) {
                session.removeAttribute(SiteMinderTestValve.SESSION_TOKEN_KEY);
                out.println("<p>Existing token removed: " + token);
                token = null;
            }

            if (token == null) {
                if ("POST".equals(request.getMethod())) {
                    token = new SiteMinderToken();
                    token.setSmuser(request.getParameter("smuser"));
                    token.setAppuser(request.getParameter("appuser"));
                    session.setAttribute(SiteMinderTestValve.SESSION_TOKEN_KEY, token);
                    out.println("Token added to session: " + token + "");
                    out.println("<p><a href='?removeToken=true'>Remove Token!</a>");
                } else {
        %>
                    <div class="row">
                        <div class="col-xs-4"></div>
                        <div class="col-xs-4">
                            <form class="form-horizontal" method="POST">
                                <div class="form-group">
                                    <label for="username">SiteMinder User:</label>
                                    <input type="input" class="form-control" id="username" name="smuser">
                                </div>
                                <div class="form-group">
                                    <label for="appuser">App User:</label>
                                    <input type="input" class="form-control" id="appuser" name="appuser">
                                </div>
                                <button class="btn btn-success">Submit</button>
                            </form>
                        </div>
                        <div class="col-xs-4"></div>
                    </div>
        <%
                }
            } else {
                out.println("<p>Token already exists in session: " + token);
                out.println("<p><a href='?removeToken=true'>Remove Token!</a>");
            }
        %>
    </div>
</div>

</html>
