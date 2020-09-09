<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello World</h1>
<a href="${pageContext.request.contextPath}/injectData">
    <img src="https://media.2oceansvibe.com/wp-content/uploads/2019/07/teletubbies.jpg">
</a>
<table border="1" bgcolor="#d3d3d3">
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/registration">Proceed to registration</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/users/all">Check users</a>
        </td>
    </tr>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/addProduct">Add products</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/products/warehouse">Check warehouse</a>
        </td>
    </tr>
</table>
</body>
</html>
