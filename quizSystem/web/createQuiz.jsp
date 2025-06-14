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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .btn-green {
            background-color: #28a745;
            color: white;
        }

        .btn-green:hover {
            background-color: #218838;
            color: white;
        }

        .btn-red {
            background-color: #dc3545;
            color: white;
        }

        .btn-red:hover {
            background-color: #bd2130;
            color: white;
        }
    </style>
</head>

<body>
    <jsp:include page="header.jsp" />

    <form action="CreateQuizServlet" method="post" class="container w-50 mt-5 bg-light p-4 rounded shadow">
        <h2 class="text-center mt-1 mb-4">Quiz Creation Form</h2>

        <label class="form-label mt-3" for="title">Title</label>
        <input type="text" name="title" id="title" class="form-control" required placeholder="Quiz title">

        <label class="form-label mt-3" for="description">Description</label>
        <textarea name="description" id="description" class="form-control" rows="2"
                  style="resize: vertical; overflow: auto;"></textarea>

        <label class="form-label mt-3" for="is_published">Published Status</label>
        <select name="is_published" id="is_published" class="form-select" required>
            <option value="false">Hidden</option>
            <option value="true">Visible</option>
        </select>

        <div class="mt-4">
            <input type="submit" class="btn btn-green" value="Create Quiz">
            <input type="reset" class="btn btn-red ms-3" value="Clear">
            <a href="teacherDashboard.jsp" class="btn btn-secondary ms-3">Back to Dashboard</a>
        </div>
    </form>

    <script src="teacherSide.js"></script>
    <jsp:include page="footer.jsp" />
</body>
</html>
