<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<h1> All users page</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Delete Link</th>
    </tr>
    <c:forEach var ="product" items="${users}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/users/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
