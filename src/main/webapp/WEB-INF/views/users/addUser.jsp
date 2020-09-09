<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<h1>Register here</h1>

<h1 style="color:darkred">${message}</h1>

<form method="post" action="${pageContext.request.contextPath}/users/add">
    <table bgcolor="#d3d3d3">
        <tr>
            <td>
                Please enter your name: <label>
                <input type="text" name="name">
            </label>
            </td>
        </tr>
        <tr>
            <td>
                Please enter your login: <label>
                <input type="text" name="login">
            </label>
            </td>
        </tr>
        <tr>
            <td>
                Please enter your password: <label>
                <input type="password" name="pass">
            </label>
            </td>
            <td>
                Please confirm your password: <label>
                <input type="password" name="pass-repeat">
            </label>
            </td>
        </tr>
    </table>
<button type="submit" name="register">Register</button>
</form>
</body>
</html>
