<%-- 
    Document   : assignQuiz
    Created on : 6 Jun 2025, 7:24:41 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Assign Quiz</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Assign a Quiz to Students</h2>
    
    <!-- Display the list of quizzes to choose from -->
    <form action="AssignQuizServlet" method="POST">
        <label for="quiz">Select a Quiz:</label>
        <select name="quizId" id="quiz">
            <c:forEach var="quiz" items="${quizzes}">
                <option value="${quiz.id}">${quiz.title}</option>
            </c:forEach>
        </select><br/><br/>

        <label for="students">Select Students:</label><br/>
        <c:forEach var="student" items="${students}">
            <input type="checkbox" name="students" value="${student.id}" /> ${student.name}<br/>
        </c:forEach><br/>

        <button type="submit">Assign Quiz</button>
    </form>
</body>
</html>
