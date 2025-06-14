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

        <style>
            body {
                display: flex;
                flex-direction: column;
                min-height: 100vh; /* full height */
            }

            main {
                flex: 1; /* grow to take available space */
            }

            /* Optional: just for clarity */
            footer {
                background-color: #f8f9fa;
                padding: 1rem;
                text-align: center;
            }
        </style>
    </head>
    <body style="margin: 0; padding: 0;">
        <jsp:include page="header.jsp" />
        <main class="container">
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
            <a  href="teacherDashboard.jsp">Back to Dashboard</a>
        </main>
        <jsp:include page="footer.jsp" />
    </body>
</html>
