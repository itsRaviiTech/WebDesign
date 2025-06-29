/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import beans.Option;
import beans.Question;
import beans.Submission;
import dao.AnswerDAO;
import dao.QuestionDAO;
import dao.SubmissionDAO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ravib
 */
public class ReviewAnswersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        System.out.println("SUBMISSION ID SENT TO REVIEW ANSWER IS  : " + submissionId);
        try {
            // 1. Get submission
            SubmissionDAO submissionDAO = new SubmissionDAO();
            Submission submission = submissionDAO.getSubmissionById(submissionId); // you may need to write this

            // 2. Get quizId from submission
            int quizId = submission.getQuizId();
            System.out.println("QUIZ ID AFTER GETTING SUBMISSION ID : " + quizId);
            
            // 3. Get all questions + options for the quiz
            QuestionDAO questionDAO = new QuestionDAO();
            List<Question> questions = questionDAO.getQuestionsByQuizId(quizId); // include options inside each question

            // 4. Get student selected options for this submission
            AnswerDAO answerDAO = new AnswerDAO();
            Map<Integer, Integer> selectedOptions = answerDAO.getSelectedOptionsBySubmission(submissionId);
            // result: Map<question_id, option_id>

            // 5. Inject isSelected flag into matching options
            for (Question q : questions) {
                for (Option opt : q.getOptions()) {
                    if (selectedOptions.containsKey(q.getQuestionId())) {
                        opt.setIsSelected(opt.getOptionID() == selectedOptions.get(q.getQuestionId()));
                    } else {
                        opt.setIsSelected(false);
                    }
                }
            }

            // 6. Set quiz and questions for review
            request.setAttribute("quizForReview", submission);
            request.setAttribute("questionForReview", questions);

            // 7. Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("review-answers.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp");
        }
    }
}
