<%-- 
    Document   : joke
    Created on : 30 Jun 2025, 1:33:08 am
    Author     : User
--%>

<%@ page import="model.JokeBean" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>DailyDose - Joke of the Day</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Fonts & Icons -->
        <link rel="stylesheet" href="styles.css"/>
    </head>
    <jsp:include page="header.jsp" />
    <body>

        <!-- Page Wrapper -->
        <div class="page-wrapper">

            <!-- Joke Section -->
            <div class="container joke-section">
                <%
                    String category = request.getParameter("category");
                    if (category == null || category.isEmpty()) {
                        category = "Any";
                    }
                    String type = request.getParameter("type");
                    if (type == null || type.isEmpty()) {
                        type = "single";
                    }

                    JokeBean jokeBean = new JokeBean();
                    jokeBean.fetchJoke(category, type);
                    String joke = jokeBean.getJoke();
                %>

                <!-- Joke Box -->
                <div class="joke-box">
                    <h2><i class="fas fa-laugh"></i> Joke of the Day</h2>
                    <p><%= joke%></p>
                </div>

                <!-- Joke Options Section -->
                <div class="joke-options">
                    <h3><i class="fas fa-filter"></i> Choose Your Joke Category:</h3>
                    <form method="get" action="joke.jsp" class="joke-form">
                        <select name="category" class="form-select">
                            <option value="Any" <%= category.equals("Any") ? "selected" : ""%>>Any</option>
                            <option value="Programming" <%= category.equals("Programming") ? "selected" : ""%>>Programming</option>
                            <option value="Miscellaneous" <%= category.equals("Miscellaneous") ? "selected" : ""%>>Miscellaneous</option>
                            <option value="Puns" <%= category.equals("Puns") ? "selected" : ""%>>Puns</option>
                            <option value="Spooky" <%= category.equals("Spooky") ? "selected" : ""%>>Spooky</option>
                            <option value="Christmas" <%= category.equals("Christmas") ? "selected" : ""%>>Christmas</option>
                        </select>

                        <h3><i class="fas fa-code-branch"></i> Choose Joke Type:</h3>
                        <select name="type" class="form-select">
                            <option value="single" <%= type.equals("single") ? "selected" : ""%>>Single Part</option>
                            <option value="twopart" <%= type.equals("twopart") ? "selected" : ""%>>Two Part</option>
                        </select>

                        <br/>
                        <input type="submit" value="Get Joke" class="btn btn-primary"/>
                    </form>
                </div>
            </div>
            <div class="text-center mt-4">
                <a href="studentDashboard.jsp" class="btn btn-secondary">Back to Student Dashboard</a>
            </div>
        </div>

    </body>
    <jsp:include page="footer.jsp" />
</html>
