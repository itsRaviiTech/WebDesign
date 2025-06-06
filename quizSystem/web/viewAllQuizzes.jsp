<%-- 
    Document   : viewAllQuizzes
    Created on : 6 Jun 2025, 7:25:20 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>View All Quizzes</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>All Available Quizzes</h2>
    
    <!-- Display all quizzes -->
    <table border="1">
        <thead>
            <tr>
                <th>Quiz Title</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="quiz" items="${quizzes}">
                <tr>
                    <td>${quiz.title}</td>
                    <td>${quiz.description}</td>
                    <td>
                        <a href="viewQuizDetails.jsp?quizId=${quiz.id}">View Details</a> |
                        <a href="editQuiz.jsp?quizId=${quiz.id}">Edit</a> |
                        <a href="deleteQuiz.jsp?quizId=${quiz.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
