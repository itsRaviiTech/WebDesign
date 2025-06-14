<%-- 
    Document   : studentDashboard
    Created on : 5 Jun 2025, 5:10:25 pm
    Author     : User
--%>

<html>
    <head>
        <title>Student Dashboard</title>
        <link rel="stylesheet" type="text/css" href="styles.css">

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
    <body body style="margin: 0; padding: 0;">
        <jsp:include page="header.jsp" />
        <main class="container">
            <!-- Container for content -->

            <!-- Header -->
            <h1>Welcome, Student!</h1>
            <!-- Subheader -->
            <p>View and attempt your quizzes below:</p>

            <!-- Link to view available quizzes -->
            <a href="viewAvailableQuizzes.jsp" class="box-link">View Available Quizzes</a>

        </main>
        <jsp:include page="footer.jsp" />
    </body>
</html>
