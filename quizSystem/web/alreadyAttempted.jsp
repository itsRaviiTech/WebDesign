<%-- 
    Document   : alreadyAttempted
    Created on : 30 Jun 2025, 12:53:16 am
    Author     : kirtie
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Attempt Blocked</title>
    <link rel="stylesheet" type="text/css" href="styles.css"> <!-- Include your CSS file -->
</head>

    <jsp:include page="header.jsp" />

<body>
    <div class="container">
        <div class="alert alert-danger">
            You have already completed this quiz, and reattempts are not allowed.
        </div>

        <!-- Return to Dashboard Button -->
        <div class="d-flex justify-content-center mt-4">
            <a href="studentDashboard.jsp" class="btn btn-primary">Go back to Dashboard</a>
        </div>
    </div>

</body>
<jsp:include page="footer.jsp" />
</html>
