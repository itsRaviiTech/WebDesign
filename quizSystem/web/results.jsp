<%-- 
    Document   : results
    Created on : 5 Jun 2025, 2:59:55 pm
    Author     : User
--%>

<%@ page import="java.util.List" %>
<%@ page import="beans.Submission" %>

<html>
<head>
    <title>Your Quiz Results</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Your Quiz Results</h2>
    
    <ul>
        <c:forEach var="submission" items="${submissions}">
            <li>Quiz: ${submission.quiz.title} - Score: ${submission.score}</li>
        </c:forEach>
    </ul>
</body>
</html>

