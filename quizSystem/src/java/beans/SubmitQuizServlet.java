/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package beans;

import dao.QuestionDAO;
import dao.OptionDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the quizId from the form
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        
        // Checking user session for logged-in status
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");  // Redirect to login if user is not logged in
            return;
        }
        
        // Retrieve the quiz and questions from the database
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questions = questionDAO.getQuestionsByQuizId(quizId);

        // Initialize the score
        int score = 0;

        // Process each question and check the selected answer
        for (Question question : questions) {
            String questionParam = "question_" + question.getQuestionID();
            String selectedOptionId = request.getParameter(questionParam);

            // If an option is selected, check if it's correct
            if (selectedOptionId != null) {
                OptionDAO optionDAO = new OptionDAO();
                
                // Use the correct method name: getOptionById()
                Option selectedOption = optionDAO.getOptionById(Integer.parseInt(selectedOptionId));

                // Check if the selected option is the correct answer
                if (selectedOption != null && selectedOption.getIsCorrect()) {
                    score++;  // Increment score for correct answer
                }
            }
        }

        // Optionally, store the score in the database or session if needed
        // For example: store the score for the user in the database or session
        // UserDAO userDAO = new UserDAO();
        // userDAO.saveUserScore(user.getUserId(), quizId, score);

        // Store the score in the request attribute to display on the results page
        request.setAttribute("score", score);

        // Forward the result to a results page to show the score
        request.getRequestDispatcher("viewResults.jsp").forward(request, response);
    }
}
