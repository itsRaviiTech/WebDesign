<%-- 
    Document   : viewAvailableQuizzes
    Created on : 6 Jun 2025, 8:10:20 pm
    Author     : User
--%>
<%@ page import="dao.StudentDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Available Quizzes</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>

    <body class="bg-light">
        <jsp:include page="header.jsp" />

        <%
            StudentDAO studentDao = new StudentDAO();
            List<Quiz> quizList = studentDao.getAvailableQuizzesForStudent();
        %>

        <div class="container">
            <h2 class="text-center my-4">Available Quizzes</h2>

            <% if (quizList != null && !quizList.isEmpty()) {
                    int noQuiz = 1;
                    for (Quiz quiz : quizList) {
            %>
            <div class="card shadow-hover rounded mb-4">
                <div class="card-body">
                    <h4 class="card-title text-center mb-3">Quiz <%= noQuiz%></h4>
                    <dl class="row mb-4 px-3">
                        <dt class="col-sm-3">Quiz ID</dt>
                        <dd class="col-sm-9"><%= quiz.getQuizId()%></dd>
                        <dt class="col-sm-3">Title</dt>
                        <dd class="col-sm-9"><%= quiz.getTitle()%></dd>
                        <dt class="col-sm-3">Description</dt>
                        <dd class="col-sm-9"><%= quiz.getDescription()%></dd>
                    </dl>
                    <div class="d-flex justify-content-end gap-2">
                        <a href="AttemptQuizServlet?quizID=<%= quiz.getQuizId()%>" class="btn btn-outline-primary">Attempt</a>
                    </div>
                </div>
            </div>
            <% noQuiz++;
                } %>

            <div class="text-center mt-4">
                <a href="studentDashboard.jsp" class="btn btn-secondary">Back to Student Dashboard</a>
            </div>

            <% } else { %>
            <div class="alert alert-info text-center">No quizzes available at the moment.</div>
            <div class="text-center mt-4">
                <a href="studentDashboard.jsp" class="btn btn-secondary">Back to Student Dashboard</a>
            </div>
            <% }%>
        </div>

        <jsp:include page="footer.jsp" />
    </body>
</html>
