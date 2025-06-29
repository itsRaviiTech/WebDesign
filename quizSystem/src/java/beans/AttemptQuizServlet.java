/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author kirtie
 */
import dao.QuizDAO;
import dao.QuestionDAO;
import dao.OptionDAO;
import dao.StudentDAO;
import beans.Submission;
import beans.Quiz;
import dao.QuizAssignmentsDAO;
import dao.SubmissionDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AttemptQuizServlet")
public class AttemptQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizID"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if the student has already submitted the quiz
        SubmissionDAO submissionDAO = new SubmissionDAO();
        boolean hasSubmitted = submissionDAO.checkIfStudentHasSubmitted(user.getUserId(), quizId);

        // Check if multiple attempts are allowed for the quiz
        QuizAssignmentsDAO quizAssignmentsDAO = new QuizAssignmentsDAO();
        boolean allowMultipleAttempts = quizAssignmentsDAO.isMultipleAttemptsAllowed(user.getUserId(), quizId);

        // Block reattempt if already submitted and reattempt is not allowed
        if (hasSubmitted && !allowMultipleAttempts) {
            // Redirect to a page informing the user they cannot reattempt
            response.sendRedirect("alreadyAttempted.jsp");
            return;
        }

        // Get Quiz info
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

        // Check if the student has already submitted the quiz
        SubmissionDAO submissionDAO = new SubmissionDAO();
        boolean hasSubmitted = submissionDAO.checkIfStudentHasSubmitted(user.getUserId(), quiz.getQuizId());

        // Block submission if already submitted and reattempt is not allowed
        if (hasSubmitted) {
            response.sendRedirect("alreadyAttempted.jsp"); // Redirect to a page showing the message
            return;
        }

        StudentDAO studentDao = new StudentDAO();
        int submissionID = studentDao.insertSubmission(submission);

        for (Question q : question) {
            Answer answer = new Answer();
            String paramName = "question_" + q.getQuestionID(); // Use q.getQuestionID() not q.getQuestionId() if your method is like this
            String selectedOption = request.getParameter(paramName);

            // Handle True/False questions
            if (selectedOption != null) {
                if (selectedOption.equals("true") || selectedOption.equals("false")) {
                    // For True/False, set selected option as 1 for true, 0 for false
                    answer.setSelectedOptionId(selectedOption.equals("true") ? 1 : 0);
                } else {
                    // Handle Multiple Choice (other questions)
                    try {
                        int selectedOptionId = Integer.parseInt(selectedOption);
                        answer.setSelectedOptionId(selectedOptionId);
                    } catch (NumberFormatException e) {
                        e.printStackTrace(); // Handle the exception (you could also log or handle more gracefully)
                    }
                }
            }

            answer.setSubmissionId(submissionID);
            answer.setQuestionId(q.getQuestionID());
            studentDao.insertAnswers(answer);
        }

        session.setAttribute("submissionId", submissionID);
        response.sendRedirect("GradeSubmissionServlet?submissionId=" + submissionID);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
