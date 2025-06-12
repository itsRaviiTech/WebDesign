/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import dao.UserDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;

// Add the @WebServlet annotation to map this servlet to a URL pattern
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = authenticateUser(email, password);

        if (user != null) {
            // User authenticated successfully
            HttpSession session = request.getSession();
            session.setAttribute("user", user);  // Save the user object in the session
            
            System.out.println("User authenticated: " + (user != null)); // Debug line
            System.out.println("User role: " + user.getRole()); // Debug line

            // Redirect to the appropriate dashboard based on the role
            if ("Teacher".equals(user.getRole())) {
                // Store a teacher-specific session attribute
                session.setAttribute("teacher", user);
                response.sendRedirect("teacherDashboard.jsp");  // Redirect to Teacher's dashboard
            } else if ("Student".equals(user.getRole())) {
                // Store a student-specific session attribute
                session.setAttribute("student", user);
                response.sendRedirect("studentDashboard.jsp");  // Redirect to Student's dashboard
            }
        } else {
            // Invalid credentials
            response.sendRedirect("login.jsp?error=true");  // Redirect back to login with an error
        }
    }

    // Authenticate the user by checking the email and password
    private User authenticateUser(String email, String password) {
        // Query the database and check if the email and password match
        // If they match, return the User object with the role
        UserDAO userDAO = new UserDAO();
        return userDAO.authenticate(email, password);
    }
}
