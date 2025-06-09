<%-- 
    Document   : EditQuestion
    Created on : 9 Jun 2025, 4:40:18 am
    Author     : HP
--%>

<%@page import="java.util.List"%>
<%@page import="beans.Question"%>
<%@page import="beans.Quiz"%>
<%@page import="beans.Option"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    </head>
    <body>
        <%
            Quiz quiz = (Quiz) request.getAttribute("quiz");
            List<Question> questions = (List<Question>) request.getAttribute("questionList");
        %>

        <div class="container mt-5">
            <h2>Edit Quiz</h2>
            <form action="UpdateQuizServlet" method="post">
                <input type="hidden" name="quizId" value="<%= quiz.getQuizId()%>">
                <input type="hidden" id="questionCount" name="questionCount" value="<%= questions.size()%>">

                <div class="mb-3">
                    <label class="form-label">Title:</label>
                    <input type="text" class="form-control" name="title" value="<%= quiz.getTitle()%>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Description:</label>
                    <textarea class="form-control" name="description" required><%= quiz.getDescription()%></textarea>
                </div>

                <div id="quizContainer"></div>

                <div class="text-center mt-4">
                    <button type="button" id="addQuizBtn" class="btn btn-success btn-lg">+ Add Question</button>
                </div>

                <button type="submit" class="btn btn-primary mt-3">Update Quiz</button>
            </form>
        </div>
        <%
            String jsonQuestions = new com.google.gson.Gson().toJson(questions);
        %>
        <pre><%= jsonQuestions%></pre>

        <script>
            const existingQuestions = <%= new com.google.gson.Gson().toJson(questions)%>;
        </script>
        <script src="addQuestions.js"></script>
    </body>
</html>
