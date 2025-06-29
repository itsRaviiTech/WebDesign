<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, beans.*, dao.*" %>
<jsp:useBean id="user" class="beans.User" scope="session" />
<%@ page import="dao.TeacherDao" %>

<%
    SubmissionDAO submissionDAO = new SubmissionDAO();
    TeacherDao teacherDAO = new TeacherDao();

    List<Submission> submissions = submissionDAO.getSubmissionsForTeacher(user.getUserId());
    int totalSubmissions = teacherDAO.getTotalSubmissionsByTeacher(user.getUserId());
    List<QuizStats> avgScores = teacherDAO.getAverageScoresPerQuiz(user.getUserId());

    request.setAttribute("submissions", submissions);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Teacher - View Submissions & Report</title>
        <link rel="stylesheet" href="styles.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


        <style>
            body {
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }

            main {
                flex: 1;
            }

            .report-card {
                background-color: #f8f9fa;
                padding: 1.5rem;
                border-radius: 10px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                margin-bottom: 30px;
            }

            .avg-score {
                font-weight: bold;
                color: #007bff;
            }

            .results-table {
                width: 100%;
                border-collapse: collapse;
            }

            .results-table th, .results-table td {
                padding: 12px;
                border: 1px solid #ccc;
            }

            .results-table th {
                background-color: #f2f2f2;
            }

            .error-message {
                color: #888;
                font-style: italic;
            }

            footer {
                background-color: #f8f9fa;
                padding: 1rem;
                text-align: center;
            }
        </style>
    </head>
    <body style="margin: 0; padding: 0;">
        <jsp:include page="header.jsp" />
        <main class="container mt-5">

            <h2 class="mb-4">📈 Quiz Summary Report</h2>

            <div class="report-card text-center">
                <h4>Total Submissions Received</h4>
                <p class="display-6"><%= totalSubmissions%></p>
            </div>

            <div class="report-card">
                <h4 class="mb-3">Average Score Per Quiz</h4>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Quiz Title</th>
                            <th>Average Score</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (avgScores != null && !avgScores.isEmpty()) {
                                for (QuizStats stat : avgScores) {
                        %>
                        <tr>
                            <td><%= stat.getQuizTitle()%></td>
                            <td class="avg-score"><%= String.format("%.2f", stat.getAverageScore())%></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="2" class="text-center text-muted">No submission data yet.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                <div class="mt-4">
                    <h5 class="text-center">Visual Representation</h5>
                    <canvas id="avgScoreChart" height="120"></canvas>
                </div>

            </div>

            <h2 class="mt-5 mb-3">📋 Student Submissions</h2>

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
                            <a href="ReviewAnswersServlet?submissionId=<%= sub.getSubmissionId()%>" class="btn btn-sm btn-primary" target="_blank">
                                View Answers
                            </a>
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

            <div class="mt-4">
                <a href="teacherDashboard.jsp" class="btn btn-secondary">⬅ Back to Dashboard</a>
            </div>
        </main>
        <script>
            const chartLabels = [
            <% for (QuizStats stat : avgScores) {%>
                "<%= stat.getQuizTitle().replace("\"", "\\\"")%>",
            <% } %>
            ];

            const chartData = [
            <% for (QuizStats stat : avgScores) {%>
            <%= stat.getAverageScore()%>,
            <% }%>
            ];

            const ctx = document.getElementById('avgScoreChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: chartLabels,
                    datasets: [{
                            label: 'Average Score',
                            data: chartData,
                            backgroundColor: 'rgba(54, 162, 235, 0.6)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1,
                            borderRadius: 5
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            max: 100
                        }
                    },
                    plugins: {
                        legend: {
                            display: false
                        },
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    return 'Score: ' + context.parsed.y.toFixed(2);
                                }
                            }
                        }
                    }
                }
            });
        </script>
        <jsp:include page="footer.jsp" />
    </body>
</html>
