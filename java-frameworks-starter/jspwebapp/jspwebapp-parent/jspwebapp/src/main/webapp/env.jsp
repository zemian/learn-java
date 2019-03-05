<%@ page session="false" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.TreeMap" %>
<!DOCTYPE html>
<html>
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
</html>
