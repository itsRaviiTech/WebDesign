<%-- 
    Document   : attemptQuiz
    Created on : 5 Jun 2025, 2:59:45 pm
    Author     : User
--%>

<%@ page import="java.util.List" %>
<%@ page import="beans.Quiz, beans.Question, beans.Option" %>

<html>
<head>
    <title>Attempt Quiz</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script>
        // Function to confirm before submitting the quiz
        function confirmSubmit() {
            const userConfirmed = confirm("Are you sure you want to submit the quiz? This action is irreversible.");
            return userConfirmed; // Submit form if confirmed
        }
    </script>
</head>
<body>
    <h2>Quiz: ${quiz.title}</h2>  <!-- Using EL for clean attribute access -->
    
    <form action="SubmitQuizServlet" method="POST" onsubmit="return confirmSubmit()">
        <input type="hidden" name="quizId" value="${quiz.quizId}" />
        
        <c:forEach var="question" items="${questions}">
            <p>${question.questionText}</p> <!-- Using EL for question text -->

            <c:forEach var="option" items="${question.options}">
                <input type="radio" name="question_${question.questionId}" value="${option.optionId}" /> ${option.optionText}<br/>
            </c:forEach>
        </c:forEach>

        <button type="submit">Submit Quiz</button>
    </form>
</body>
</html>
