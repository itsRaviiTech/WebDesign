/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beans;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/view-submissions")
public class ViewSubmissionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !"teacher".equals(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId = user.getUserId();  // Get the teacher's ID from the session
        List<SubmittedQuiz> submissions = new ArrayList<>();  // List to hold the submissions

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/quizsystem", "root", "");  // Your database credentials

            // SQL query written using string concatenation for JDK 8
            String sql = "SELECT " +
                         "sq.submission_id, " +
                         "sq.quiz_id, " +
                         "sq.user_id AS student_id, " +
                         "u.username AS student_name, " +
                         "sq.submitted_at AS submission_date, " +
                         "q.title AS quiz_title " +
                         "FROM submissions sq " +
                         "JOIN quiz q ON sq.quiz_id = q.quiz_id " +
                         "JOIN users u ON sq.user_id = u.user_id " +
                         "WHERE q.created_by = ? " +  // Filter by teacher's ID
                         "ORDER BY sq.submitted_at DESC";

            // Prepare the statement and set the teacher ID
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, teacherId);  // Set teacher's ID in the query

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Process the result set and create SubmittedQuiz objects
            while (rs.next()) {
                SubmittedQuiz s = new SubmittedQuiz();
                s.setSubmissionId(rs.getInt("submission_id"));
                s.setQuizId(rs.getInt("quiz_id"));
                s.setStudentId(rs.getInt("student_id"));
                s.setStudentName(rs.getString("student_name"));
                s.setSubmissionDate(rs.getTimestamp("submission_date"));
                s.setQuizTitle(rs.getString("quiz_title"));
                submissions.add(s);  // Add each submission to the list
            }

            // Close the connection
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();  // Print any errors
        }

        // Set the submissions as a request attribute and forward to JSP
        request.setAttribute("submissions", submissions);
        request.getRequestDispatcher("teacher_view_submissions.jsp").forward(request, response);
    }
}
