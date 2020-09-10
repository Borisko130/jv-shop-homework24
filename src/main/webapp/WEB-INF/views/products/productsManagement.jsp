<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products management page</title>
</head>
<body>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete product</th>
    </tr>
    <c:forEach var ="product" items="${products}">
    <tr>
        <td>
            <c:out value="${product.id}"/>
        </td>
        <td>
            <c:out value="${product.name}"/>
        </td>
        <td>
            <c:out value="${product.price}"/>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/products/delete?id=${product.id}">Delete product</a>
        </td>
    </tr>
    </c:forEach>
</table>
<table>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/products/add">Add products</a>
        </td>
    </tr>
</table>
</body>
</html>
