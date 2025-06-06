<%-- 
    Document   : attemptQuiz
    Created on : 5 Jun 2025, 2:59:45 pm
    Author     : User
--%>

<%@page import="java.util.List"%>
<%@ page import="beans.Quiz, beans.Question, beans.Option" %>
<html>
<head>
    <title>Attempt Quiz</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Quiz: <%= request.getAttribute("quiz").getTitle() %></h2>
    <form action="SubmitQuizServlet" method="POST">
        <input type="hidden" name="quizId" value="<%= request.getAttribute("quiz").getQuizId() %>" />
        <%
            List<Question> questions = (List<Question>) request.getAttribute("questions");
            for (Question question : questions) {
        %>
            <p><%= question.getQuestionText() %></p>
            <%
                List<Option> options = question.getOptions();
                for (Option option : options) {
            %>
                <input type="radio" name="question_<%= question.getQuestionId() %>" value="<%= option.getOptionId() %>" /> <%= option.getOptionText() %><br/>
            <% } %>
        <% } %>
        <button type="submit">Submit Quiz</button>
    </form>
</body>
</html>
