<%-- 
    Document   : viewResults
    Created on : 6 Jun 2025, 8:10:50 pm
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Your Quiz Results</title>
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
    <body style="margin: 0; padding: 0;">
        <jsp:include page="header.jsp" />
        <main class="container">
            <h2>Your Quiz Results</h2>

            <c:if test="${not empty results}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>Quiz Title</th>
                            <th>Score</th>
                            <th>Submitted At</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="result" items="${results}">
                            <tr>
                                <td>${result.quiz.title}</td> <!-- Assuming result contains quiz object -->
                                <td>${result.score}</td>
                                <td>${result.submittedAt}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>

            <c:if test="${empty results}">
                <p>No results found for you.</p>
            </c:if>

            <br>
            <a href="studentDashboard.jsp">Back to Student Dashboard</a>
        </main>
        <br/>
        <jsp:include page="footer.jsp" />
    </body>
</html>

