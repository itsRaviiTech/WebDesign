<%-- 
    Document   : quizResult
    Created on : 12 Jun 2025, 1:30:22 pm
    Author     : ravib
--%>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Quiz Result</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 2em;
                background-color: #f0f8ff;
            }
            .container {
                background-color: #fff;
                padding: 2em;
                border-radius: 10px;
                box-shadow: 0 0 10px #ccc;
                max-width: 500px;
                margin: auto;
                text-align: center;
            }
            .score {
                font-size: 2em;
                margin-top: 1em;
                color: #0077cc;
            }
            .back-button {
                margin-top: 2em;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Quiz Submitted!</h1>

            <%
                int score = (int) request.getAttribute("score");
                int totalPoints = (int) request.getAttribute("totalPoints");
            %>

            <div class="score">
                You scored <strong><%= score %></strong> out of <strong><%= totalPoints %></strong>
            </div>

            <div class="back-button">
<!--                <a href="studentDashboard.jsp">← Back to Dashboard</a>-->
            </div>
        </div>
    </body>
</html>
