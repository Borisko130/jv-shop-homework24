<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<h1 style="color:darkred">${errorMessage}</h1>
<form method="post" action="${pageContext.request.contextPath}/login">
    <table bgcolor="#d3d3d3">
        <tr>
            <td>
                Please enter your login: <label>
                <input type="text" name="login">
            </label>
            </td>
            <td>
                Please enter your password: <label>
                <input type="password" name="pass">
            </label>
            </td>
        </tr>
    </table>
    <button type="submit" name="login">Log in</button>
</form>
<a href="${pageContext.request.contextPath}/users/add">You can register here</a>
<a href="${pageContext.request.contextPath}/inject-data">Inject Data</a>
</body>
</html>
