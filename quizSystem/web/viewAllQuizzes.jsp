<%-- 
    Document   : viewAllQuizzes
    Created on : 6 Jun 2025, 7:25:20 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="dao.QuizDAO" %>
<%@page import="beans.Quiz" %>
<%@page import="java.util.*" %>

<html>
    <head>
        <title>View All Quizzes</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <style>
            /* Optional: finer control */
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
        <jsp:include page="header.jsp" />
        <jsp:useBean id="user" class="beans.User" scope="session" />
        <%
            int userID = user.getUserId();

            QuizDAO quizdDAO = new QuizDAO();
            List<Quiz> quizList = new ArrayList<>();

            quizList = quizdDAO.getQuizzesByUserID(userID);

            int noQuiz = 1;
            for (Quiz quiz : quizList) {
        %>
        <div class="container my-4">
            <div class="card shadow-hover rounded">
                <div class="card-body">
                    <h4 class="card-title text-center mb-3">Quiz <%= noQuiz%></h4>
                    <dl class="row mb-4 px-3">
                        <dt class="col-sm-3">Quiz ID</dt>
                        <dd class="col-sm-9"><%= quiz.getQuizId()%></dd>
                        <dt class="col-sm-3">Title</dt>
                        <dd class="col-sm-9"><%= quiz.getTitle()%></dd>
                        <dt class="col-sm-3">Description</dt>
                        <dd class="col-sm-9"><%= quiz.getDescription()%></dd>
                        <dt class="col-sm-3">Created At</dt>
                        <dd class="col-sm-9"><%= quiz.getCreatedAt()%></dd>
                        <dt class="col-sm-3">Status</dt>
                        <%
                            if (!quiz.isIsPublished()) {
                        %>
                        <dd class="col-sm-9"><span class="badge bg-danger">Hidden</span></dd>
                        <%
                        } else {
                        %>
                        <dd class="col-sm-9"><span class="badge bg-success">Active</span></dd>
                        <%}%>
                    </dl>
                    <div class="d-flex justify-content-end gap-2">
                        <a href="SubmitQuizServlet?QuizID=<%= quiz.getQuizId()%>" class="btn btn-outline-primary">Edit</a>
                        <a href="DeleteQuizServlet?QuizID=<%= quiz.getQuizId()%>" class="btn btn-outline-danger"
                           onclick="return confirm('Are you sure you want to delete this quiz?');">Delete</a>
                    </div>
                </div>
            </div>
            <div class="text-center mt-4">
                <a href="studentDashboard.jsp" class="btn btn-secondary">Back to Student Dashboard</a>
            </div>
        </div>

        <%
                noQuiz++;
            }
        %>
        <jsp:include page="footer.jsp" />
    </body>
</html>
