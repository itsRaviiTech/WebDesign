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
import beans.Question;
import beans.Option;
import beans.Quiz;
import dao.QuestionDAO;
import dao.OptionDAO;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
public class SubmitQuizServlet extends HttpServlet {

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
            out.println("<title>Servlet SubmitQuizServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubmitQuizServlet at " + request.getContextPath() + "</h1>");
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

        int questionCount = Integer.parseInt(request.getParameter("questionCount"));

        List<Question> questionList = new ArrayList<>();

        //
        //Create session object to retrieve the QuizID
        HttpSession session = request.getSession();
        int quizId = (int) session.getAttribute("QuizID");

        //creating all the DAO object
        QuestionDAO questionDao = new QuestionDAO();
        OptionDAO optionsDao = new OptionDAO();

        for (int i = 0; i < questionCount; i++) {
            int questionID = 0;
            String questionText = request.getParameter("questionText" + i);
            int points = Integer.parseInt(request.getParameter("points" + i));

            List<Option> options = new ArrayList<>();

            for (int j = 1; j <= 4; j++) {
                String optionText = request.getParameter("optionText_" + i + "_" + j);
                boolean isCorrect = request.getParameter("isCorrect_" + i + "_" + j) != null;

                Option opt = new Option();
                opt.setOptionText(optionText);
                opt.setIsCorrect(isCorrect);
                options.add(opt);
            }

            Question question = new Question();
            question.setQuizid(quizId);
            question.setQuestionText(questionText);
            question.setPoints(points);
            question.setOptions(options);
            question.setType("Multiple Choice");
            question.setOrderIndex(i + 1);
            
            questionList.add(question);

            questionID = questionDao.insertQuestion(question);
            if (questionID > -1) {
                for (Option option : options) {
                    optionsDao.insertOptions(option, questionID);
                }
            response.sendRedirect("teacherDashboard.jsp");
            }
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
