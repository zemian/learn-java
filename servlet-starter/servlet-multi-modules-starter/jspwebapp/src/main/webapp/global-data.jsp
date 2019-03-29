<%@ page import="jspwebapp.common.GlobalData" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<pre>
<%
    GlobalData gd = GlobalData.getInstance();
    out.println("== App Global Data List (size=" + gd.getSize() + ")");
    Set<String> names = gd.getNames();
    for (String name : names) {
        out.println(name + " = " + gd.getData(name));
    }
%>
</pre>
</html>
