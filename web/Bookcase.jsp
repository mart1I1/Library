<%--
  Created by IntelliJ IDEA.
  User: Fedor
  Date: 15.08.2018
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bookcase</title>
</head>
<body>
<c:choose>
    <c:when test="${not empty userName}">
        <p>
            <a href="/userProfile">${userName}</a>
        </p>
    </c:when>
    <c:otherwise>
        <p>
            <a href="/singIn">Вход</a>
        </p>
    </c:otherwise>
</c:choose>

<c:forEach var="book" items="${books}">
    <a href="/books?bookId=${book.getId()}">${book.getTitle()}</a>
    <br>
</c:forEach>


</body>
</html>
