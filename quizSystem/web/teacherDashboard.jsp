<%-- 
    Document   : teacherDashboard
    Created on : 5 Jun 2025, 5:09:48 pm
    Author     : User
--%>

<%@ page import="java.util.List" %>
<%@ page import="beans.Quiz" %>

<html>
    <head>
        <title>Teacher Dashboard</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .shadow-hover:hover {
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.25);
                transition: box-shadow 0.3s ease;
            }
        </style>
    </head>

    <body>
        <jsp:useBean id="user" class="beans.User" scope="session" />

        <div class="container d-flex align-items-center justify-content-center flex-column ">
            <div class="text-center mt-5 mb-5">
                <h1>Welcome, Dr. <jsp:getProperty name="user" property="name" /> </h1>

                <p class="mx-auto mt-3 w-75 p-3 rounded">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti natus
                    aliquid itaque
                    nam
                    eius modi, exercitationem tempore similique, est deserunt neque voluptatibus. Repellendus modi doloremque rerum
                    neque
                    laudantium, dignissimos aut.
                </p>

                <a href="createQuiz.jsp" class="btn bg-dark text-white rounded-sm mt-3 me-2">Create new quiz</a>
                <a href="viewAllQuizzes.jsp" class="btn border-dark text-muted mt-3">View my quiz</a>
            </div>

            <div class="container mt-5">
                <div class="d-flex justify-content-between text-center flex-wrap">
                    <!-- Dynamically Display Quizzes -->
                    <c:forEach var="quiz" items="${quizList}">
                        <div class="card rounded shadow-hover bd-light p-3 mx-2 flex-fill" style="max-width: 30%;">
                            <p><strong>${quiz.title}</strong></p>
                            <p>${quiz.description}</p>
                            <div class="d-flex justify-content-between">
                                <a class="btn" href="editQuiz.jsp?quizId=${quiz.quizId}">Edit</a>
                                <a class="btn text-danger" href="assignQuiz.jsp?quizId=${quiz.quizId}">Hide</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
