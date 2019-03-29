<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<body>
<h1>Hello World</h1>
<p>This is a Simple JSP App.</p>
<script>
    document.documentElement.innerHTML = "<pre>" +
        document.documentElement.innerHTML.replace(/</g,"&lt;") +
        "</pre>";
</script>
</body>
</html>