<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, beans.*, dao.*" %>
<jsp:useBean id="user" class="beans.User" scope="session" />
<%
    SubmissionDAO submissionDAO = new SubmissionDAO();
    List<Submission> submissions = submissionDAO.getSubmissionsForTeacher(user.getUserId());
    request.setAttribute("submissions", submissions);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Teacher - View Submissions</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <div class="container">
            <h2>Student Submissions</h2>

            <%
                if (submissions == null || submissions.isEmpty()) {
            %>
            <p class="error-message">No quiz submissions found for your quizzes yet.</p>
            <%
            } else {
            %>
            <table class="results-table">
                <thead>
                    <tr>
                        <th>Student Name</th>
                        <th>Quiz Title</th>
                        <th>Submission Date</th>
                        <th>Score</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Submission sub : submissions) {
                    %>
                    <tr>
                        <td><%= sub.getStudentName()%></td>
                        <td><%= sub.getQuizTitle()%></td>
                        <td><%= sub.getSubmittedAt()%></td>
                        <td><%= sub.getScore()%></td>
                        <td>
                            <a href="view_submission_detail.jsp?submissionID=<%= sub.getSubmissionId()%>" class="btn">View</a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <%
                }
            %>
        </div>
    </body>
</html>
