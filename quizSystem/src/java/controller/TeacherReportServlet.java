/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import beans.QuizStats;
import beans.User;
import dao.TeacherDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

public class TeacherReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId = user.getUserId();
        TeacherDao teacherDAO = new TeacherDao();

        int totalSubmissions = teacherDAO.getTotalSubmissionsByTeacher(teacherId);
        List<QuizStats> avgScores = teacherDAO.getAverageScoresPerQuiz(teacherId);

        request.setAttribute("totalSubmissions", totalSubmissions);
        request.setAttribute("avgScores", avgScores);

        RequestDispatcher dispatcher = request.getRequestDispatcher("teacherReport.jsp");
        dispatcher.forward(request, response);
    }
}
