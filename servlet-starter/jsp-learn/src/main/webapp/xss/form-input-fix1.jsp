<%--
HTML injection in form testing:
FirstName:
    a"><script>alert("hello");</script>
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<!DOCTYPE html>
<html>
<body>
<%
    if ("POST".equals(request.getMethod())) {
        out.println("<pre>");
        out.println("Processing form: " + request.getRequestURI());
        // Capture form data
        String[] names = {"firstName", "lastName"};
        Map<String, String> formData = new HashMap<>();
        for (String name : names) {
            String value = request.getParameter(name);
            formData.put(name, value);
            // Repopulate request attributes with form parameters
            request.setAttribute(name, StringEscapeUtils.escapeHtml4(value));
        }

        // This is way of how Java can encode data from backend.
        out.println("<!--\nFormData Collected: " +
                StringEscapeUtils.escapeHtml4(formData.toString()) + "--!>");
        out.println("</pre>");
    }
%>
<h1>HTML Input Sanitizing: Fix with Java Backend Encoding</h1>
<form method="POST">
    First Name:<br>
    <input type="text" name="firstName" value="${firstName}" size="60"><br>
    Last Name:<br>
    <input type="text" name="lastName" value="${lastName}" size="60"><br><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
