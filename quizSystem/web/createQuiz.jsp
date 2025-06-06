<%-- 
    Document   : createQuiz
    Created on : 5 Jun 2025, 2:59:31 pm
    Author     : User
--%>

<%@ page import="beans.Quiz, dao.QuizDAO" %>
<html>
<head>
    <title>Create Quiz</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Create a New Quiz</h2>
    <form action="CreateQuizServlet" method="POST">
        <label>Quiz Title:</label>
        <input type="text" name="title" required /><br/>
        <label>Description:</label>
        <textarea name="description" required></textarea><br/>
        <label>Publish:</label>
        <input type="checkbox" name="isPublished" /><br/>
        <button type="submit">Create Quiz</button>
    </form>
</body>
</html>

