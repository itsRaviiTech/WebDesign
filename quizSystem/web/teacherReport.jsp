<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, beans.QuizStats" %>

<%
    int totalSubmissions = (int) request.getAttribute("totalSubmissions");
    List<QuizStats> avgScores = (List<QuizStats>) request.getAttribute("avgScores");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Teacher Quiz Report</title>
    <link rel="stylesheet" href="styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .report-card {
            background-color: #f8f9fa;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 0 12px rgba(0,0,0,0.08);
            margin-bottom: 30px;
        }
        .avg-score {
            font-weight: bold;
            color: #007bff;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container mt-5">
    <h2 class="text-center mb-4">📊 Your Quiz Performance Report</h2>

    <!-- Total Submissions -->
    <div class="report-card text-center">
        <h4>Total Submissions Received</h4>
        <p class="display-6"><%= totalSubmissions %></p>
    </div>

    <!-- Average Score per Quiz -->
    <div class="report-card">
        <h4 class="mb-3">Average Scores Per Quiz</h4>
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
                    <td><%= stat.getQuizTitle() %></td>
                    <td class="avg-score"><%= String.format("%.2f", stat.getAverageScore()) %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="2" class="text-center text-muted">No quiz submissions found yet.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <div class="text-center mt-4">
        <a href="teacherDashboard.jsp" class="btn btn-secondary">⬅ Back to Dashboard</a>
    </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
