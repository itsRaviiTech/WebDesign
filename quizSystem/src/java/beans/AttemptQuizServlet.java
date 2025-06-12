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
import dao.OptionDAO;
import beans.Quiz;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AttemptQuizServlet")
public class AttemptQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizID"));

        //get Quiz info
        QuizDAO quizDao = new QuizDAO();
        Quiz quiz = quizDao.getQuizById(quizId);

        QuestionDAO questionDao = new QuestionDAO();
        List<Question> question = questionDao.getQuestionsByQuizId(quizId);

        OptionDAO optionDao = new OptionDAO();
        List<Option> optionList = optionDao.getOptionByQuizID(quizId);

        // If quiz not found, redirect to an error page
        if (quiz == null) {
            request.setAttribute("errorMessage", "Quiz not found.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();

        request.setAttribute("quizForAttempt", quiz);
        session.setAttribute("questionForAttempt", question);
        request.setAttribute("optionForAttempt", optionList);

        request.getRequestDispatcher("attemptQuiz.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        List<Question> question = (List<Question>) session.getAttribute("questionForAttempt");
        String[] questionIds = {"101", "102"};
        PrintWriter out = response.getWriter();
        for (Question q : question) {
            
            String paramName = "question_" + q.getQuestionID(); // Use q.getQuestionID() not q.getQuestionId() if your method is like this
            String selectedOptionId = request.getParameter(paramName);
            out.println("Question ID: " + q.getQuestionID() + " → Selected Option ID: " + selectedOptionId);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
