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

        int teacherId = user.getUserId();
        List<SubmittedQuiz> submissions = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/your_db_name", "your_db_user", "your_db_pass");

            String sql = """
                SELECT sq.submission_id, sq.quiz_id, sq.student_id, u.username AS student_name,
                       sq.submission_date, q.title AS quiz_title
                FROM submitted_quiz sq
                JOIN quiz q ON sq.quiz_id = q.quiz_id
                JOIN users u ON sq.student_id = u.user_id
                WHERE q.teacher_id = ?
                ORDER BY sq.submission_date DESC
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SubmittedQuiz s = new SubmittedQuiz();
                s.setSubmissionId(rs.getInt("submission_id"));
                s.setQuizId(rs.getInt("quiz_id"));
                s.setStudentId(rs.getInt("student_id"));
                s.setStudentName(rs.getString("student_name"));
                s.setSubmissionDate(rs.getTimestamp("submission_date"));
                s.setQuizTitle(rs.getString("quiz_title"));
                submissions.add(s);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("submissions", submissions);
        request.getRequestDispatcher("teacher_view_submissions.jsp").forward(request, response);
    }
}
