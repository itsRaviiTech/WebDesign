/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import dao.QuizDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet("/ViewQuizzesServlet")
public class ViewQuizzesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all quizzes from the database
        QuizDAO quizDAO = new QuizDAO();
        List<Quiz> quizzes = quizDAO.getAllQuizzes();

        // Debugging: Print out the size of the quizzes list to ensure it's not empty
        System.out.println("Number of quizzes fetched: " + quizzes.size());

        // Set quizzes as request attribute to display in the JSP
        request.setAttribute("quizzes", quizzes);

        // Debugging: Print out each quiz title to ensure quizzes are being fetched
        for (Quiz quiz : quizzes) {
            System.out.println("Quiz Title: " + quiz.getTitle());
        }

        // Forward the request to viewAllQuizzes.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewAllQuizzes.jsp");
        dispatcher.forward(request, response);
    }
}
