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
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description") != "" ? request.getParameter("description") : "Good Luck" ;
        boolean isPublished = Boolean.parseBoolean(request.getParameter("isPublished"));

        HttpSession session = request.getSession();
        int createdBy = ((User) session.getAttribute("user")).getUserId();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setIsPublished(isPublished);
        quiz.setCreatedBy(createdBy);

        QuizDAO quizDAO = new QuizDAO();
        int id = quizDAO.createQuiz(quiz);
        
        if (id > - 1) {
            session.setAttribute("QuizID", id);
            response.sendRedirect("addQuestions.jsp");
        } else {
            response.sendRedirect("createQuiz.jsp?error=Error creating quiz");
        }
    }
}