/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import dao.SubmissionDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet("/getResults")
public class GetResultsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId")); // Assuming userId is passed as a parameter

        SubmissionDAO submissionDAO = new SubmissionDAO();
        try {
            List<Submission> results = submissionDAO.getResultsByUser(userId); // Get the results for the user

            // Set results as a request attribute to be accessed in the JSP
            request.setAttribute("results", results);

            // Forward the request to a JSP to display the results
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewResults.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            // Handle the exception
            
        }
    }
}
