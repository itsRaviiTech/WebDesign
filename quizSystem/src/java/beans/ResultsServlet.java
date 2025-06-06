/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author User
 */
import dao.SubmissionDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

@WebServlet("/results")
public class ResultsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        SubmissionDAO submissionDAO = new SubmissionDAO();
        List<Submission> submissions = null;
        try {
            submissions = submissionDAO.getResultsByUser(user.getUserId());
        } catch (SQLException ex) {
            Logger.getLogger(ResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("submissions", submissions);
        request.getRequestDispatcher("results.jsp").forward(request, response);
    }
}