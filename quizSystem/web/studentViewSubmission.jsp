<%-- 
    Document   : studentViewSubmission
    Created on : 29 Jun 2025, 10:16:55 pm
    Author     : ravib
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, beans.*, dao.*" %>
<jsp:useBean id="user" class="beans.User" scope="session" />
<%
    SubmissionDAO submissionDAO = new SubmissionDAO();
    List<Submission> submissions = submissionDAO.getSubmissionsByStudent(user.getUserId()); // new method
    request.setAttribute("submissions", submissions);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>My Quiz Submissions</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body style="margin: 0; padding: 0;">
        <jsp:include page="header.jsp" />
        <main class="container">
            <h2>My Submission History</h2>

            <%
                if (submissions == null || submissions.isEmpty()) {
            %>
            <p class="error-message">You have not submitted any quizzes yet.</p>
            <%
            } else {
            %>
            <table class="results-table">
                <thead>
                    <tr>
                        <th>Quiz Title</th>
                        <th>Submission Date</th>
                        <th>Score</th>
                        <th>Review</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Submission sub : submissions) {
                    %>
                    <tr>
                        <td><%= sub.getQuizTitle()%></td>
                        <td><%= sub.getSubmittedAt()%></td>
                        <td><%= sub.getScore()%></td>
                        <td>  <a href="ReviewAnswersServlet?submissionId=<%= sub.getSubmissionId() %>">View</a></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <%
                }
            %>
            <br><br>
            <a href="studentDashboard.jsp">Back to Dashboard</a>
        </main>
        <jsp:include page="footer.jsp" />
    </body>
</html>
