<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="includes/html-head.jsp"%>
<%@include file="includes/header.jsp"%>

<div class="container">
    <div class="main-app">
        <h1>Settings Table</h1>
        <table class="table">
            <c:forEach var="setting" items="${settings}">
                <tr>
                    <td>${setting.id}</td>
                    <td>${setting.category}</td>
                    <td>${setting.name}</td>
                    <td>${setting.value}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%@include file="includes/footer.jsp"%>
</html>