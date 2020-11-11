<!DOCTYPE html>
<html>
<pre>
== Testing servlet resource finder by classpath
    * Use parameter "res" to find classpath resource.
    * Use parameter "class" to find class by name.

<%
    String res = request.getParameter("res");
    if (res != null) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        out.println("Resource " + res + " = " + cl.getResource(res));
    }

    String className = request.getParameter("class");
    if (className != null) {
        Class clzz = Class.forName(className);
        out.println("Class " + className + " = " + clzz.getProtectionDomain().getCodeSource().getLocation());
    }
%>
</pre>
</html>
