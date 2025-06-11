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
        <link rel="stylesheet" type="text/css" href="login.css">
    </head>
    <body>
        <div>
            <div class="login-image">
                <img src="loginpic.jpg" />
            </div>
            <div class="login-form">
                <h2>Login</h2>
                <form action="LoginServlet" method="POST">
                    <label>Email:</label>
                    <input type="text" name="email" required /><br/>
                    <label>Password:</label>
                    <input type="password" name="password" required /><br/>
                    <button type="submit">Login</button>
                </form>
                <p class="register-text">
                    Don't have an account?
                    <a href="register.jsp">Click here to register</a>
                </p>
            </div>
        </div>
        <%= request.getParameter("error") != null ? "Invalid credentials, please try again." : ""%>
    </body>
</html>
