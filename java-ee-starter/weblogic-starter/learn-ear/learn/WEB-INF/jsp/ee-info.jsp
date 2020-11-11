<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<body>
<pre>

<%
out.println("== Java EE API Version Info");
Map<String, String> versionMap = (Map<String, String>)request.getAttribute("versionMap");
for (Map.Entry<String, String> e : versionMap.entrySet()) {
	out.println(e.getKey() + "=" + e.getValue());
}
out.println("");

out.println("== Java EE Implementation Class");
Map<String, String> classNameMap = (Map<String, String>)request.getAttribute("implClassMap");
for (Map.Entry<String, String> e : classNameMap.entrySet()) {
	out.println(e.getKey() + "=" + e.getValue());
}
out.println("");

out.println("== Java EE Class Locations");
Map<String, String> classLocMap = (Map<String, String>)request.getAttribute("classLocMap");
for (Map.Entry<String, String> e : classLocMap.entrySet()) {
	out.println(e.getKey() + "=" + e.getValue());
}
out.println("");

out.println("== Java EE TagLibs");
Map<String, String> tagLibsMap = (Map<String, String>)request.getAttribute("tagLibsMap");
for (Map.Entry<String, String> e : tagLibsMap.entrySet()) {
	out.println(e.getKey() + "=" + e.getValue());
}
out.println("");
%>

</pre>
</body>
</html>