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
<!--        <link rel="stylesheet" type="text/css" href="styles.css">-->
        <link rel="stylesheet" type="text/css" href="login.css">
    </head>
    <body>
        <!-- Container for login page content -->
        <div class="login-container">
            <!-- Image section -->
            <div class="login-image">
                <img src="loginimg.jpg" alt="Login Image" />
            </div>

            <!-- Form section -->
            <div class="login-form">
                <h2>Login</h2>

                <!-- Error message -->
                <%= request.getParameter("error") != null ? "<p class='error-message'>Invalid credentials, please try again.</p>" : "" %>

                <!-- Login form -->
                <form action="LoginServlet" method="POST">
                    <!-- Email input -->
                    <label for="email">Email:</label>
                    <input type="text" id="email" name="email" required />

                    <!-- Password input -->
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required />

                    <!-- Submit button -->
                    <button type="submit">Login</button>
                </form>

                <!-- Registration link -->
                <p class="register-text">
                    Don't have an account?
                    <a href="register.jsp">Click here to register</a>
                </p>
            </div>
        </div>
    </body>
</html>
