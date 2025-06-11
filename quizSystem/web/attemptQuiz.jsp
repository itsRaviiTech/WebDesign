<%-- 
    Document   : attemptQuiz
    Created on : 5 Jun 2025, 2:59:45 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Attempt Quiz</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <!-- Ensure the quiz is not null before trying to access it -->
    <h2>Quiz: <c:out value="${quiz.title}" /></h2>
    
    <form action="SubmitQuizServlet" method="POST">
        <!-- Hidden input to store quiz ID -->
        <input type="hidden" name="quizId" value="${quiz.quizId}" />
        
        <!-- Loop through questions -->
        <c:forEach var="question" items="${questions}">
            <p><c:out value="${question.questionText}" /></p>
            
            <!-- Loop through options for each question -->
            <c:forEach var="option" items="${question.options}">
                <input type="radio" name="question_${question.questionId}" value="${option.optionId}" />
                <c:out value="${option.optionText}" /><br/>
            </c:forEach>
        </c:forEach>

        <button type="submit">Submit Quiz</button>
    </form>
</body>
</html>
