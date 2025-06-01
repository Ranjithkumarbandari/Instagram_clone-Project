<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Instagram Clone - Login</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<h2>Login</h2>

<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>

<form action="login" method="post">
    <label for="username">Username:</label><br/>
    <input type="text" id="username" name="username" required /><br/><br/>

    <label for="password">Password:</label><br/>
    <input type="password" id="password" name="password" required /><br/><br/>

    <input type="submit" value="Login" />
</form>

<p>Don't have an account? <a href="register.jsp">Register here</a></p>


</body>
</html>