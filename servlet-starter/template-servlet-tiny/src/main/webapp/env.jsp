<%@ page session="false" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<body>
<pre>
== OS Environments
<%
    Map<String, String> env = new TreeMap<>(System.getenv());
    for (Map.Entry<String, String> entry : env.entrySet()) {
        out.println(entry.getKey() + " = " + entry.getValue());
    }
%>

== System Properties
<%
    Map<Object, Object> props = new TreeMap<>(System.getProperties());
    for (Map.Entry<Object, Object> entry : props.entrySet()) {
        out.println(entry.getKey() + " = " + entry.getValue());
    }
%>
</pre>
</body>
</html>
