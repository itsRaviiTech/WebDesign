<%-- 
    Document   : viewQuizDetails
    Created on : 6 Jun 2025, 7:32:41 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quiz Details</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Quiz Details</h2>

    <c:if test="${not empty quiz}">
        <h3>Title: ${quiz.title}</h3>
        <p>Description: ${quiz.description}</p>
    </c:if>

    <c:if test="${empty quiz}">
        <p>Quiz not found.</p>
    </c:if>

    <br/><br/>
    <a href="viewAllQuizzes.jsp">Back to All Quizzes</a>
</body>
</html>
