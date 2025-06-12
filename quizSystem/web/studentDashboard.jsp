<%-- 
    Document   : studentDashboard
    Created on : 5 Jun 2025, 5:10:25 pm
    Author     : User
--%>

<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    
</head>
<body>
        <jsp:include page="header.jsp" />

    <!-- Container for content -->
    <div class="container">
        <!-- Header -->
        <h1>Welcome, Student!</h1>
        <!-- Subheader -->
        <p>View and attempt your quizzes below:</p>

        <!-- Link to view available quizzes -->
        <a href="viewAvailableQuizzes.jsp" class="box-link">View Available Quizzes</a>

        <!-- Link to view submitted results -->
        <a href="viewResults.jsp" class="box-link">View My Results</a>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>
