<%-- 
    Document   : login
    Created on : 5 Jun 2025, 2:59:07 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*, java.io.*" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <form action="LoginServlet" method="POST">
        <label>Email:</label>
        <input type="text" name="email" required /><br/>
        <label>Password:</label>
        <input type="password" name="password" required /><br/>
        <button type="submit">Login</button>
    </form>
    <%= request.getParameter("error") != null ? "Invalid credentials, please try again." : "" %>
</body>
</html>
