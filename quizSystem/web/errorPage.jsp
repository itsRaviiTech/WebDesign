<%-- 
    Document   : errorPage
    Created on : 6 Jun 2025, 7:55:31 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Error</h2>
    
    <!-- Display the error message passed from the servlet -->
    <p>${errorMessage}</p>
    
    <a href="index.jsp">Go back to homepage</a> <!-- Optional: link to redirect users back to a safe page -->
</body>
</html>
