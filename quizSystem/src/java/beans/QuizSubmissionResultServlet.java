/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/quiz-result")
public class QuizSubmissionResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        int earnedPoints = 0;
        int totalPoints = 0;
        int scorePercentage = 0;

        Connection conn = null;
        PreparedStatement updateCorrectStmt = null;
        PreparedStatement scoreCalcStmt = null;
        PreparedStatement updateSubmissionStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            // Step 1: Mark correct answers
            String updateCorrectSQL
                    = "UPDATE answer a "
                    + "JOIN option o ON a.selected_option_id = o.option_id "
                    + "SET a.is_correct = o.is_correct "
                    + "WHERE a.submission_id = ?";
            updateCorrectStmt = conn.prepareStatement(updateCorrectSQL);
            updateCorrectStmt.setInt(1, submissionId);
            updateCorrectStmt.executeUpdate();

            // Step 2: Calculate earned and total points
            String scoreCalcSQL
                    = "SELECT "
                    + "SUM(CASE WHEN a.is_correct = 1 THEN q.points ELSE 0 END) AS earned_points, "
                    + "SUM(q.points) AS total_points "
                    + "FROM answer a "
                    + "JOIN question q ON a.question_id = q.question_id "
                    + "WHERE a.submission_id = ?";
            scoreCalcStmt = conn.prepareStatement(scoreCalcSQL);
            scoreCalcStmt.setInt(1, submissionId);
            rs = scoreCalcStmt.executeQuery();

            if (rs.next()) {
                earnedPoints = rs.getInt("earned_points");
                totalPoints = rs.getInt("total_points");
                if (totalPoints > 0) {
                    scorePercentage = (earnedPoints * 100) / totalPoints;
                }
            }

            // Step 3: Update submission record
            String updateSubmissionSQL
                    = "UPDATE submission "
                    + "SET score = ?, submitted_at = NOW() "
                    + "WHERE submission_id = ?";
            updateSubmissionStmt = conn.prepareStatement(updateSubmissionSQL);
            updateSubmissionStmt.setInt(1, scorePercentage);
            updateSubmissionStmt.setInt(2, submissionId);
            updateSubmissionStmt.executeUpdate();

            // Step 4: Show results
            request.setAttribute("scorePercentage", scorePercentage);
            request.setAttribute("earnedPoints", earnedPoints);
            request.setAttribute("totalPoints", totalPoints);
            request.getRequestDispatcher("/quiz_result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error calculating quiz result.");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (updateCorrectStmt != null) {
                    updateCorrectStmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (scoreCalcStmt != null) {
                    scoreCalcStmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (updateSubmissionStmt != null) {
                    updateSubmissionStmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
