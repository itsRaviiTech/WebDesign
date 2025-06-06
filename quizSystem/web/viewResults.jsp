<%-- 
    Document   : viewResults
    Created on : 6 Jun 2025, 8:10:50 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Your Quiz Results</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Your Quiz Results</h2>

    <c:if test="${not empty results}">
        <table border="1">
            <thead>
                <tr>
                    <th>Quiz Title</th>
                    <th>Score</th>
                    <th>Submitted At</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="result" items="${results}">
                    <tr>
                        <td>${result.quiz.title}</td> <!-- Assuming result contains quiz object -->
                        <td>${result.score}</td>
                        <td>${result.submittedAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty results}">
        <p>No results found for you.</p>
    </c:if>

    <br/>
    <a href="studentDashboard.jsp">Back to Student Dashboard</a>  <!-- Optional, if needed -->
</body>
</html>

