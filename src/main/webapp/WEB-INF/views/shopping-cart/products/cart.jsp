<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your shopping cart</title>
</head>
<body>

<h1 style="color:darkred">${message}</h1>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Remove from cart</th>
    </tr>
    <c:forEach var ="product" items="${cart}">
        <tr>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping-cart/products/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
