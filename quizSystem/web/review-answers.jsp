<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, beans.*, com.google.gson.Gson" %>
<%
    Submission submission = (Submission) request.getAttribute("quizForReview");
    List<Question> questions = (List<Question>) request.getAttribute("questionForReview");

    if (questions == null) {
        questions = new ArrayList<>();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Review Answers</title>
    <link rel="stylesheet" href="styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .highlight-correct {
            background-color: #d4edda;
            border-left: 5px solid #28a745;
        }

        .highlight-wrong {
            background-color: #f8d7da;
            border-left: 5px solid #dc3545;
        }

        .highlight-missed {
            background-color: #fff3cd;
            border-left: 5px solid #ffc107;
        }

        .quiz-block {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container mt-5">
        <h2 class="text-center mb-4">Review: <%= submission.getQuizTitle() %></h2>

        <div id="quizContainer"></div>

        <div class="text-center mt-4">
            <a href="studentDashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
        </div>
    </div>

    <script>
        const quizData = <%= new Gson().toJson(questions) %>;
    </script>
    <script src="reviewQuiz.js"></script>

    <jsp:include page="footer.jsp" />
</body>
</html>
