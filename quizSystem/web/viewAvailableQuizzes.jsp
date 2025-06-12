<%-- 
    Document   : viewAvailableQuizzes
    Created on : 6 Jun 2025, 8:10:20 pm
    Author     : User
--%>

<%@page import="dao.StudentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="beans.Quiz" %>

<html>
<head>
    <title>Available Quizzes</title>
    <link rel="stylesheet" type="text/css" href="styles.css"> <!-- Ensure the correct link -->
</head>

<body class="bg-light">
    <jsp:include page="header.jsp" />

<%
    StudentDAO studentDao = new StudentDAO();
    List<Quiz> quizList = studentDao.getAvailableQuizzesForStudent();

    if (quizList != null && !quizList.isEmpty()) {
        int noQuiz = 1;
%>

<!-- Wrap everything in ONE container -->
<div class="container">
    <h2 class="text-center my-4">Available Quizzes</h2>

    <% for (Quiz quiz : quizList) { %>
        <div class="quiz-card shadow-hover rounded">
            <div class="quiz-card-body">
                <h4 class="quiz-title text-center mb-3">Quiz <%= noQuiz %></h4>
                <dl class="quiz-details">
                    <dt>Quiz ID</dt>
                    <dd class="text-center"><%= quiz.getQuizId() %></dd>

                    <dt>Title</dt>
                    <dd class="text-center"><%= quiz.getTitle() %></dd>

                    <dt>Description</dt>
                    <dd class="text-center"><%= quiz.getDescription() %></dd>
                </dl>
                <div class="quiz-actions">
                    <a href="AttemptQuizServlet?QuizID=<%= quiz.getQuizId() %>" class="btn">Attempt</a>
                </div>
            </div>
        </div>
    <% noQuiz++; } %>

    <!-- This button stays INSIDE the same container, at the bottom -->
    <div class="quiz-actions">
        <a href="studentDashboard.jsp" class="btn">Back to Student Dashboard</a>
    </div>
</div>

<% } else { %>
    <div class="container">
        <h2 class="text-center my-4">Available Quizzes</h2>
        <div class="alert text-center">No quizzes available at the moment.</div>
        <div class="quiz-actions">
            <a href="studentDashboard.jsp" class="btn">Back to Student Dashboard</a>
        </div>
    </div>
<% } %>
    <jsp:include page="footer.jsp" />
</body>
</html>
