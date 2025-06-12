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

        // Check if quizList is not null and has data
        if (quizList != null && !quizList.isEmpty()) {
            int noQuiz = 1;  // To number the quizzes
            for (Quiz quiz : quizList) {
    %>
        <div class="container my-4">
            <div class="card shadow-hover rounded">
                <div class="card-body">
                    <h4 class="card-title text-center mb-3">Quiz <%= noQuiz %></h4>
                    <dl class="row mb-4 px-3">
                        <dt class="col-sm-3">Quiz ID</dt>
                        <dd class="col-sm-9"><%= quiz.getQuizId() %></dd>
                        <dt class="col-sm-3">Title</dt>
                        <dd class="col-sm-9"><%= quiz.getTitle() %></dd>
                        <dt class="col-sm-3">Description</dt>
                        <dd class="col-sm-9"><%= quiz.getDescription() %></dd>
                    </dl>
                    <div class="d-flex justify-content-end gap-2">
                        <a href="AttemptQuizServlet?quizID=<%= quiz.getQuizId() %>" class="btn btn-outline-primary">Attempt</a>
                    </div>
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
