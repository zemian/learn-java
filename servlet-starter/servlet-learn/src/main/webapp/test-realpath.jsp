<%@ page import="java.util.Set" %>
<!DOCTYPE html>
<html>
<pre>

## Use parameter "res" to find web resource real path using Servlet Context.

<%
    String res = request.getParameter("res");
    if (res != null) {
        out.println("Application getRealPath " + res + " = " + application.getRealPath(res));
        out.println("Application getResource " + res + " = " + application.getResource(res));
        Set<String> names = application.getResourcePaths(res);
        if (names != null) {
            for (String name : names) {
                out.println("Application getResourcePaths " + res + " = " + name);
            }
        }
    }
%>
</pre>
</html>
