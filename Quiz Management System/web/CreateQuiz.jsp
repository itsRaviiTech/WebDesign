<%-- 
    Document   : CreateQuiz
    Created on : 5 Jun 2025, 11:26:39 pm
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .btn-green {
                background-color: #28a745;
                color: white;
            }

            .btn-green:hover {
                background-color: #218838;
                color: white;
            }

            .btn-red {
                background-color: #dc3545;
                color: white;
            }

            .btn-red:hover {
                background-color: #bd2130;
                color: white;
            }
        </style>
    </head>

    <body>
        <form action="QuizCreattinServlet.java" method="post" class="container mt-5 bg-light p-4 rounded shadow">
            <h2 class="text-center mt-1 mb-4">Quiz Creation Form</h2>

            <label class="form-label mt-3" for="title">Title</label>
            <input type="text" name="title" id="title" class="form-control" required placeholder="Quiz title">

            <label class="form-label mt-3" for="description">Description</label>
            <textarea type="text" name="description" id="description" class="form-control" rows="2"
                      style="resize: vertical; overflow: auto;"></textarea>

            <label class="form-label mt-3" for="is_published">Status</label>
            <select name="is_published" id="is_published" class="form-select" required>
                <option value="false">Hidden</option>
                <option value="true">Visible</option>
            </select>

            <input type="submit" class="btn btn-green mt-4">
            <input type="reset" class="btn btn-red mt-4 ms-3" value="Clear">
        </form>


        
        <script>
            const select = document.getElementById("is_published");

            select.addEventListener('change', function () {
                if (this.value === "false") {
                    this.classList.remove('text-success');
                    this.classList.add('text-danger');

                } else if (this.value === "true") {
                    this.classList.remove('text-danger');
                    this.classList.add('text-success');
                } else {
                    this.classList.remove('text-success', 'text-danger')
                }
            });

            //Triger on the page Load
            select.dispatchEvent(new Event('change'));
        </script>
    </body>

</html>
