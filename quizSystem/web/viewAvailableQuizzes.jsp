<%-- 
    Document   : viewAvailableQuizzes
    Created on : 6 Jun 2025, 8:10:20 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Available Quizzes</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script>
        // Function to confirm before navigating to attempt quiz page
        function confirmAttempt(quizId) {
            const userConfirmed = confirm("Are you sure you want to attempt this quiz?");
            if (userConfirmed) {
                // Redirect the student to the attempt quiz page with the quiz ID
                window.location.href = `attemptQuiz.jsp?quizId=${quizId}`;
            }
        }
    </script>
</head>
<body>
    <h2>Available Quizzes</h2>

    <c:if test="${not empty quizzes}">
        <table border="1">
            <thead>
                <tr>
                    <th>Quiz Title</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="quiz" items="${quizzes}">
                    <tr>
                        <td>${quiz.title}</td>
                        <td>${quiz.description}</td>
                        <td>
                            <button onclick="confirmAttempt(${quiz.id})">Attempt</button> |
                            <a href="viewQuizDetails.jsp?quizId=${quiz.id}">View Details</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty quizzes}">
        <p>No quizzes available at the moment.</p>
    </c:if>

    <br/>
    <a href="studentDashboard.jsp">Back to Student Dashboard</a>  <!-- Optional, if needed -->
</body>
</html>
