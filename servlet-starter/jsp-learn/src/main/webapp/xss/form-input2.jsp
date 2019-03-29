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

    function decodeHtml(html) {
        var txt = document.createElement("textarea");
        txt.innerHTML = html;
        return txt.value;
    }

    function processForm() {
        var testForm = document.getElementById("testForm");
        var output = document.getElementById("output");
        var input = "<img src='x' onerror='alert(123);'>";
        //var input = testForm.firstName.value;
        //var input = decodeHtml(testForm.firstName.value);
        console.log("input: " + input);
        output.innerHTML = escapeHtml(input); // Allow XSS attack
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
