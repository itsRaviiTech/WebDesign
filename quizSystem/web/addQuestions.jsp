<%-- 
    Document   : addquestions.jsp
    Created on : 6 Jun 2025, 7:50:41 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <title>Multiple Choice Quiz Test</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>

    <body>
        <div class="container mt-4">
            <h2>Add Multiple Choice Quiz</h2>
            <form  action="SubmitQuizServlet" method="post" id="quizForm">
                <input type="hidden" id="questionCount" name="questionCount" value="0" />
         
                <div id="quizContainer"></div>

                <div class="text-center mt-4">
                    <button type="button" id="addQuizBtn" class="btn btn-success btn-lg">+ Add Question</button>
                </div>

                <div class="mt-4 text-center">
                    <button type="submit" class="btn btn-primary">Submit Quiz</button>
                </div>
            </form>
        </div>

        <script src="addQuestions.js"></script>
    </body>
</html>