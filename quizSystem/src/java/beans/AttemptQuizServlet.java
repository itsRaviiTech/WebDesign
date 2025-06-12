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
import dao.StudentDAO;
import dao.AnswerDAO;
import beans.Submission;
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

        session.setAttribute("quizForAttempt", quiz);
        session.setAttribute("questionForAttempt", question);
        request.setAttribute("optionForAttempt", optionList);

        request.getRequestDispatcher("attemptQuiz.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<Question> question = (List<Question>) session.getAttribute("questionForAttempt");
        Quiz quiz = (Quiz) session.getAttribute("quizForAttempt");
        User user = (User) session.getAttribute("user");

        Submission submission = new Submission();
        submission.setUserId(user.getUserId());
        submission.setQuizId(quiz.getQuizId());
//        PrintWriter out = response.getWriter();


        StudentDAO studentDao = new StudentDAO();
        int submissionID = studentDao.insertSubmission(submission);
        for (Question q : question) {
            Answer answer = new Answer();
            String paramName = "question_" + q.getQuestionID(); // Use q.getQuestionID() not q.getQuestionId() if your method is like this
            int selectedOptionId = Integer.parseInt(request.getParameter(paramName));
            answer.setSubmissionId(submissionID);
            answer.setQuestionId(q.getQuestionID());
            answer.setSelectedOptionId(selectedOptionId);
            studentDao.insertAnswers(answer);
        }
        
        request.getRequestDispatcher("grade-submission?submissionId=" + submissionID).forward(request, response);

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
