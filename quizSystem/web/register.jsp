<%-- 
    Document   : register
    Created on : 12 Jun 2025, 3:58:16 am
    Author     : ravib
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*, java.io.*" %>
<html>
    <head>
        <title>Register</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <link rel="stylesheet" type="text/css" href="login.css">
    </head>
    <body>
        <div>
            <div class="login-image">
                <img src="loginimg.jpg" />
            </div>
            <div class="login-form">
                <h2>Register</h2>
                <form action="RegisterServlet" method="POST">
                    <label>Username:</label>
                    <input type="text" name="username" required /><br/>

                    <label>Email:</label>
                    <input type="email" name="email" required /><br/>

                    <label>Password:</label>
                    <input type="password" name="password" required /><br/>

                    <label>Role:</label>
                    <select name="role" required>
                        <option value="student">Student</option>
                        <option value="teacher">Teacher</option>
                    </select><br/>

                    <button type="submit">Register</button>
                </form>
                <p class="register-text">
                    Already have an account?
                    <a href="login.jsp">Click here to login</a>
                </p>
            </div>
        </div>
        <%= request.getParameter("error") != null ? "Registration failed, please try again." : ""%>
    </body>
</html>