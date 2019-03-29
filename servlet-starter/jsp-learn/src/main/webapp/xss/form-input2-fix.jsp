<%--
HTML injection in form testing:
FirstName:
    <script>alert("hello");</script>
--%>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script>
    function escapeHtml(str) {
        return String(str)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;');
    }
    </script>

    <script>
    function processForm() {
        var output = document.getElementById("output");
        var input = "<img src='x' onerror='alert(123);'>";
        input = escapeHtml(input);
        console.log("input: " + input);
        output.innerHTML = input;
    }
    </script>
</head>
<body>

<h1>Hello World</h1>
<p>This is a Simple JSP App.</p>

<div id="output">PLACEHOLDER</div>

<p/>
<p/>
<form id="testForm" method="POST">
    First Name:<br>
    <input type="text" name="firstName" value="" size="60"><br>
    Last Name:<br>
    <input type="text" name="lastName" value="" size="60"><br><br>
    <input type="button" value="Submit" onclick="processForm();"/>
</form>

</body>
</html>
