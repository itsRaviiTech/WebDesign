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
    <link rel="stylesheet" type="text/css" href="styles.css">
    <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />-->
    <style>
        .shadow-hover {
            transition: box-shadow 0.3s ease, transform 0.3s ease;
        }

        .shadow-hover:hover {
            transform: translateY(-4px);
            box-shadow: 0 0.75rem 1.5rem rgba(0, 0, 0, 0.35);
        }
    </style>
</head>

<body class="bg-light">
    <h2 class="text-center my-4">Available Quizzes</h2>

    <!-- Link to go back to Student Dashboard -->
    <div class="container mb-4">
        <a href="studentDashboard.jsp" class="btn btn-outline-secondary">Back to Student Dashboard</a>
    </div>

    <%
        
        StudentDAO studentDao = new StudentDAO();
        List<Quiz> quizList = new ArrayList<>();
        
        quizList = studentDao.getAvailableQuizzesForStudent();
        // Retrieve the list of quizzes from the request attribute
        //List<Quiz> quizList = (List<Quiz>) request.getAttribute("quizzes");

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
                        <a href="AttemptQuizServlet?QuizID=<%= quiz.getQuizId() %>" class="btn btn-outline-primary">Attempt</a>
                    </div>
                </div>
            </div>
        </div>
    <%
            noQuiz++;  // Increment the quiz number
            }
        } else {
    %>
        <p class="text-center">No quizzes available at the moment.</p>
        <a href="studentDashboard.jsp" class="btn btn-outline-secondary">Back to Student Dashboard</a>
    <%
        }
    %>
</body>
</html>
