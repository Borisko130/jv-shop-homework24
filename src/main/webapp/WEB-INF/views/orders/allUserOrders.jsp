<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your orders</title>
</head>
<body>
<h1>Your orders</h1>
<table border="1">
    <tr>
        <th>Id</th>
        <th>View details</th>
    </tr>
    <c:forEach var="order" items="${orders}" varStatus="count">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/details?id=${order.id}">
                    Check details</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
