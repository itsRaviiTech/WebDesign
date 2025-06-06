/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import dao.QuizDAO;
import java.io.IOException;
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
@WebServlet("/viewQuizDetails")
public class ViewQuizDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the quizId from the request (ensure it's parsed as an integer)
        int quizId = Integer.parseInt(request.getParameter("quizId"));  // Convert quizId to int

        QuizDAO quizDAO = new QuizDAO();
        
        // Call QuizDAO to fetch the quiz by ID
        Quiz quiz = quizDAO.getQuizById(quizId);  // Use the updated method

        // If quiz is not found, redirect to an error page
        if (quiz == null) {
            request.setAttribute("errorMessage", "Quiz not found.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return;
        }

        // Pass the quiz object to the JSP
        request.setAttribute("quiz", quiz);
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewQuizDetails.jsp");
        dispatcher.forward(request, response);
    }
}
