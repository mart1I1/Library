<%--
  Created by IntelliJ IDEA.
  User: Fedor
  Date: 28.08.2018
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SingIn</title>
</head>
<body>

<form action="/singIn" method="post">
    <input type="text" name="email"/>
    <input type="text" name="password"/>
    <input type="submit"/>
</form>

<form action="/registration" method="get">
    <input type="submit" value="Registration">
</form>

</body>
</html>
