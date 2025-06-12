/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import dao.SubmissionDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet("/viewResults")
public class ViewResultsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");  // Get the current user from session
        
        // Fetch results for the user
        SubmissionDAO submissionDAO = new SubmissionDAO();
        List<Submission> results = null;
        try {
            results = submissionDAO.getResultsByUser(user.getUserId());
        } catch (SQLException ex) {
            Logger.getLogger(ViewResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set results as request attribute
        request.setAttribute("results", results);

        // Forward to JSP to display the results
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewResults.jsp");
        dispatcher.forward(request, response);
    }
}
