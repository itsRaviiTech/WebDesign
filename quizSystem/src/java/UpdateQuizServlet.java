/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Quiz;
import beans.Question;
import beans.Option;
import dao.QuestionDAO;
import dao.QuizDAO;
import dao.OptionDAO;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author HP
 */
public class UpdateQuizServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateQuizServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateQuizServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        // Set response content type
        response.setContentType("text/html;charset=UTF-8");

        // Get all parameters
        Map<String, String[]> paramMap = request.getParameterMap();

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Parameters Debug</title></head><body>");
            out.println("<h2>Received Parameters</h2>");
            out.println("<table border='1' cellpadding='5' cellspacing='0'>");
            out.println("<tr><th>Parameter Name</th><th>Value(s)</th></tr>");

            if (paramMap != null && !paramMap.isEmpty()) {
                for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                    out.print("<tr><td>" + entry.getKey() + "</td><td>");
                    String[] values = entry.getValue();
                    for (int i = 0; i < values.length; i++) {
                        out.print(values[i]);
                        if (i < values.length - 1) {
                            out.print(", ");
                        }
                    }
                    out.println("</td></tr>");
                }
            } else {
                out.println("<tr><td colspan='2'>No parameters received.</td></tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
        }

        System.out.println("----- END DEBUG -----");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        QuizDAO quizDAO = new QuizDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        OptionDAO optionsDAO = new OptionDAO();

        int quizId = Integer.parseInt(request.getParameter("quizId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean isPublished = Boolean.parseBoolean(request.getParameter("is_published"));
        int questionCount = Integer.parseInt(request.getParameter("questionCount"));

        //create an quiz object
        Quiz quiz = new Quiz();
        //insert data into the object
        quiz.setQuizId(quizId);
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setIsPublished(isPublished);
        
        //calling DOA to update to Database
        quizDAO.UpdateQuiz(quiz);

        List<Question> questionList = new ArrayList<>();

        for (int i = 0; i < questionCount; i++) {
            String questionIdStr = request.getParameter("questionId" + i);
            int questionId = (questionIdStr != null && !questionIdStr.isEmpty()) ? Integer.parseInt(questionIdStr) : -1;

            String questionText = request.getParameter("questionText" + i);
            int points = Integer.parseInt(request.getParameter("points" + i));

            List<Option> optionList = new ArrayList<>();

            for (int j = 1; j <= 4; j++) {
                String optionIdStr = request.getParameter("optionId_" + i + "_" + j);
                int optionId = (optionIdStr != null && !optionIdStr.isEmpty()) ? Integer.parseInt(optionIdStr) : -1;

                String optionText = request.getParameter("optionText_" + i + "_" + j);
                boolean isCorrect = request.getParameter("isCorrect_" + i + "_" + j) != null;

                Option option = new Option();
                option.setOptionID(optionId);
                option.setOptionText(optionText);
                option.setIsCorrect(isCorrect);
               
                optionList.add(option);
                
            }

            Question question = new Question();
            question.setQuestionID(questionId);
            question.setQuestionText(questionText);
            question.setType("Multiple Choice");
            question.setOrderIndex(i + 1);
            question.setPoints(points);
            question.setQuizid(quizId);
            question.setOptions(optionList);


            questionList.add(question);
        }

        for (Question question : questionList) {
            try {
                boolean updatedStatus = questionDAO.UpdateQuestion(question);  // Tries to update questions/options
                if (!updatedStatus) {
                    throw new Exception("Question update failed for question ID: " + question.getQuestionID());
                }
                for (Option option : question.getOptions()) {
                    boolean optionUpdated = optionsDAO.UpdateOptionsByOptionID(option);
                    if (!optionUpdated) {
                        throw new Exception("Option update failed for option ID: " + option.getOptionID());
                    }
                }
            } catch (Exception e) {    
                int questionsID = questionDAO.insertQuestion(question);
                for (Option option : question.getOptions()) {
                    boolean optionInsert = optionsDAO.insertOptions(option, questionsID);
                }
            }
        }
        
        request.getRequestDispatcher("viewAllQuizzes.jsp").forward(request, response);
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
