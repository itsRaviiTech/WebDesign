<%-- 
    Document   : attemptQuiz
    Created on : 5 Jun 2025, 2:59:45 pm
    Author     : User
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="beans.Quiz, beans.Question, beans.Option" %>
<html>
    <head>
        <title>Attempt Quiz</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>
    <body>
        <%
            Quiz quiz = (Quiz) request.getAttribute("quizForAttempt");
            List<Question> questions = (List<Question>) session.getAttribute("questionForAttempt");
            if (questions == null) {
                questions = new ArrayList<>();
            }
        %>

        <div class="container mt-5">
            <div class="text-center m-2">
                <h2>Quiz ID: <%= quiz.getTitle()%></h2>
            </div>
            <form action="AttemptQuizServlet" method="post">
                <!-- Hidden container for JS to render quiz -->
                <div id="quizContainer"></div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary btn-lg">Submit Quiz</button>
                </div>
            </form>
        </div>
             <%
            String jsonQuestions = new com.google.gson.Gson().toJson(questions);
        %>
        <pre><%= jsonQuestions%></pre>
        <script>
        const quizData = <%= new com.google.gson.Gson().toJson(questions)%>;
        </script>
        <script src="studentSide.js"></script>
    </body>
</html>
