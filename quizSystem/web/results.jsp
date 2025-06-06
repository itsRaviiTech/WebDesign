<%-- 
    Document   : results
    Created on : 5 Jun 2025, 2:59:55 pm
    Author     : User
--%>

<%@page import="java.util.List"%>
<%@ page import="beans.Submission" %>
<html>
<head>
    <title>Your Quiz Results</title>
    <link rel="stylesheet" type="text/css" href="styles.css">

</head>
<body>
    <h2>Your Quiz Results</h2>
    <ul>
        <%
            List<Submission> submissions = (List<Submission>) request.getAttribute("submissions");
            for (Submission submission : submissions) {
        %>
            <li>Quiz: <%= submission.getQuiz().getTitle() %> - Score: <%= submission.getScore() %></li>
        <% } %>
    </ul>
</body>
</html>
