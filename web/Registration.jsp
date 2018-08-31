<%--
  Created by IntelliJ IDEA.
  User: Fedor
  Date: 28.08.2018
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

<form action="/registration" method="post">
    <p>
        <b>Имя:</b>
        <input type="text" name="name">
    </p>
    <p>
        <b>Возраст:</b>
        <input type="text" name="age">
    </p>
    <p>
        <b>Email:</b>
        <input type="text" name="email">
    </p>
    <p>
        <b>Пароль:</b>
        <input type="text" name="password">
    </p>
    <p>
        <input type="submit">
    </p>
</form>

</body>
</html>
