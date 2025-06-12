/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import dao.StudentDAO;  // Import StudentDAO to fetch quizzes for students
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/viewAvailableQuizzes")
public class ViewAvailableQuizzesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in and is a student
        HttpSession session = request.getSession();  // Get the session
        User user = (User) session.getAttribute("user");  // Retrieve the logged-in user

        if (user == null || !"Student".equals(user.getRole())) {
            // If the user is not logged in or is not a student, redirect to the login page
            response.sendRedirect("login.jsp");
            return;  // Stop further processing
        }
        
        // Proceed to fetch quizzes if logged in (whether teacher or student)
        StudentDAO studentDAO = new StudentDAO();
        List<Quiz> quizzes = studentDAO.getAvailableQuizzesForStudent();  // Fetch published quizzes for students
        
        // Debugging statement to ensure quizzes are fetched
        System.out.println("Number of quizzes fetched: " + quizzes.size());  // Debugging line
        
        // Set quizzes as a request attribute to pass to the JSP
        request.setAttribute("quizzes", quizzes);
        
        // Forward to the JSP to display the quizzes
        request.getRequestDispatcher("viewAvailableQuizzes.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Process POST requests if necessary (e.g., form submissions or data updates)
        
        // For now, we'll just redirect to doGet to reuse the same logic
        doGet(request, response);  // Reuse the doGet method to display quizzes
    }
}
