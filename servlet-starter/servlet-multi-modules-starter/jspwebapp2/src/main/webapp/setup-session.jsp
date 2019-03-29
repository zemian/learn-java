<%@ page import="jspwebapp.common.Setting" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<pre>
<%
    // Create some session data for testing.
    HttpSession sess = request.getSession(false);
    if (sess != null) {
        if (request.getParameter("removeSession") != null) {
            String id = sess.getId();
            sess.invalidate();
            out.println("Removed session: " + id);
        } else if (request.getParameter("addSession") != null) {
            Setting fooSetting = new Setting("foo", "bar");
            sess.setAttribute("fooSetting", fooSetting);
            out.println("Saved session fooSetting=" + fooSetting + ", identityHashCode=" + System.identityHashCode(fooSetting));
        } else {
            out.println("Found session: " + sess.getId());
        }
    } else {
        if (request.getParameter("createSession") != null) {
            sess = request.getSession(true);
            out.println("New session created: " + sess.getId());
        } else {
            out.println("There is no user session created yet.");
        }
    }
%>
</pre>
</html>
