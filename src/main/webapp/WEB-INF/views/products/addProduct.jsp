<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products addition page</title>
</head>
<body>
<h1>You can add products here</h1>

<form method="post" action="${pageContext.request.contextPath}/products/add">
    <table bgcolor="#d3d3d3">
        <tr>
            <td>
                Please enter name of the product: <label>
                <input type="text" name="name">
            </label>
            </td>
            <td>
                Please enter price of the product: <label>
                <input type="number" name="price">
            </label>
            </td>
        </tr>
    </table>
    <button type="submit" name="addProduct">Add product</button>
</form>
</body>
</html>
