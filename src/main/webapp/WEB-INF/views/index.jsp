<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
</head>
<body>
<h1>Hello World</h1>
<a href="${pageContext.request.contextPath}/inject-data">Inject Data</a>
<table border="1" bgcolor="#d3d3d3">
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/products/all">Check warehouse</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/shopping-cart/products">Check cart</a>
        </td>
    </tr>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/orders/all">Check orders</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </td>
    </tr>
    <tr>
    <tr>
        <td colspan="2" align="center">
           <b>ADMIN ONLY</b>
        </td>
    </tr>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/products/manage">Products management</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/orders/manage">Orders management</a>
        </td>
    </tr>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/users">Check users</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/products/add">Add products</a>
        </td>
    </tr>
</table>
</body>
</html>
