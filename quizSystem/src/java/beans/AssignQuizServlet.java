/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import dao.QuizDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet("/AssignQuizServlet")
public class AssignQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the selected quiz and student IDs from the request
        String quizId = request.getParameter("quizId");
        String[] studentIds = request.getParameterValues("students");

        // Logic to assign the quiz to the selected students
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.assignQuizToStudents(quizId, studentIds);

        // Redirect to a success page or back to the assign page
        response.sendRedirect("assignQuiz.jsp?success=true");
    }
}
