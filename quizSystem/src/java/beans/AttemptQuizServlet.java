/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author User
 */

import dao.QuizDAO;
import dao.QuestionDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttemptQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        
        // Checking user session for logged-in status
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");  // Redirect to login if the user is not logged in
            return;
        }

        QuizDAO quizDAO = new QuizDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        
        Quiz quiz = quizDAO.getQuizById(quizId);
        
        // If quiz not found, redirect to an error page
        if (quiz == null) {
            request.setAttribute("errorMessage", "Quiz not found.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return;
        }
        
        List<Question> questions = null;
        try {
            questions = questionDAO.getQuestionsByQuizId(quizId);
        } catch (Exception ex) {
            Logger.getLogger(AttemptQuizServlet.class.getName()).log(Level.SEVERE, "Error fetching questions for quizId: " + quizId, ex);
            request.setAttribute("errorMessage", "There was an issue fetching the quiz questions. Please try again later.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return;
        }
        
        // Check if there are no questions
        if (questions == null || questions.isEmpty()) {
            request.setAttribute("errorMessage", "No questions available for this quiz.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return;
        }

        // Pass the quiz and questions to the JSP
        request.setAttribute("quiz", quiz);
        request.setAttribute("questions", questions);
        request.getRequestDispatcher("attemptQuiz.jsp").forward(request, response);
    }
}
