<%-- 
    Document   : teacherDashboard
    Created on : 5 Jun 2025, 5:09:48 pm
    Author     : User
--%>

<html>
    <head>
        <title>Teacher Dashboard</title>
        <link rel="stylesheet" type="text/css" href="styles.css">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .shadow-hover:hover {
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.25);
                transition: box-shadow 0.3s ease;
            }

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
        <jsp:useBean id="user" class="beans.User" scope="session" />

        <main class="container d-flex align-items-center justify-content-center flex-column ">
            <div class="text-center mt-5 mb-5">
                <h1>Welcome, Dr. <jsp:getProperty name="user" property="name" /> </h1>

                <p class="mx-auto mt-3 w-75 p-3 rounded">
                    A heartfelt thank you to our dedicated lecturer, Dr. 
                    Your unwavering commitment, passion for teaching, and 
                    guidance mean the world to us. We truly appreciate your 
                    efforts in shaping the minds of our students. 
                    Sending you our deepest gratitude and warmest wishes!
                </p>

                <a href="createQuiz.jsp" class="btn border-dark text-muted mt-3 me-2">Create new quiz</a>
                <a href="viewAllQuizzes.jsp" class="btn border-dark text-muted mt-3 me-2">View my quiz</a>
                <a href="teacherViewSubmission.jsp" class="btn border-dark text-muted mt-3">View Submissions</a>

            </div>


            <!-- NOT USING ANYMORE -->
            <!--            <div class="container mt-5">
                            <div class="d-flex justify-content-between text-center flex-wrap">
                                <div class="card rounded shadow-hover bd-light p-3 mx-2 flex-fill" style="max-width: 30%;">
                                    <p><strong> Quiz 1 </strong></p>
                                    <p>Description</p>
                                    <div class="d-flex justify-content-between">
                                        <a class="btn" href="editQuiz.java">Edit</a>
                                        <a class="btn text-danger" href="assignQuiz.jsp">Hidden</a>
                                    </div>
                                </div>
                                <div class="card rounded shadow-hover bd-light p-3 mx-2 flex-fill" style="max-width: 30%;">
                                    <p>Quiz 2</p>
                                    <p>Description</p>
                                    <div class="d-flex justify-content-between">
                                        <a class="btn " href="editQuiz.java">Edit</a>
                                        <a class="btn text-danger" href="assignQuiz.jsp">Hidden</a>
                                    </div>
                                </div>
                                <div class="card raunded shadow-hover bd-light p-3 mx-2 flex-fill" style="max-width: 30%;">
                                    <p>Quiz 3</p>
                                    <p>Description</p>
                                    <div class="d-flex justify-content-between">
                                        <a class="btn" href="editquiz.java">Edit</a>
                                        <a class="btn text-danger" href="assignQuiz.jsp">Hidden</a>
                                    </div>
                                </div>
                            </div>
                        </div>-->
        </main>
        <jsp:include page="footer.jsp" />
    </body>

</html>


