/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beans;

import beans.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author ravib
 */
@WebServlet("/grade-submission")
public class GradeSubmissionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            int totalScore = 0;
            int totalAvailablePoints = 0;

            // 1. Get all answers for this submission
            String answerSQL = "SELECT answer_id, question_id, selected_option_id FROM answer WHERE submission_id = ?";
            stmt = conn.prepareStatement(answerSQL);
            stmt.setInt(1, submissionId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int answerId = rs.getInt("answer_id");
                int questionId = rs.getInt("question_id");
                int selectedOptionId = rs.getInt("selected_option_id");
                int isCorrect = 0;

                // 2. Check if selected option is correct
                String checkSQL = "SELECT is_correct FROM options WHERE option_id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
                checkStmt.setInt(1, selectedOptionId);
                ResultSet checkRs = checkStmt.executeQuery();

                if (checkRs.next() && checkRs.getInt("is_correct") == 1) {
                    isCorrect = 1;

                    // 3. If correct, get question points
                    String pointSQL = "SELECT points FROM questions WHERE question_id = ?";
                    PreparedStatement pointStmt = conn.prepareStatement(pointSQL);
                    pointStmt.setInt(1, questionId);
                    ResultSet pointRs = pointStmt.executeQuery();
                    if (pointRs.next()) {
                        totalScore += pointRs.getInt("points");
                    }
                    pointRs.close();
                    pointStmt.close();
                }

                checkRs.close();
                checkStmt.close();

                // 4. Update is_correct in answer table
                String updateAnswerSQL = "UPDATE answer SET is_correct = ? WHERE answer_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateAnswerSQL);
                updateStmt.setInt(1, isCorrect);
                updateStmt.setInt(2, answerId);
                updateStmt.executeUpdate();
                updateStmt.close();
            }

            rs.close();
            stmt.close();

            // 5. Update score in submission table
            String updateSubmissionSQL = "UPDATE submissions SET score = ? WHERE submission_id = ?";
            PreparedStatement updateSubmissionStmt = conn.prepareStatement(updateSubmissionSQL);
            updateSubmissionStmt.setInt(1, totalScore);
            updateSubmissionStmt.setInt(2, submissionId);
            updateSubmissionStmt.executeUpdate();
            updateSubmissionStmt.close();

            // 6. Get total possible score for this quiz
            String totalPointsSQL = "SELECT SUM(points) AS totalPoints FROM questions WHERE quiz_id = (SELECT quiz_id FROM submissions WHERE submission_id = ?)";
            PreparedStatement totalPointsStmt = conn.prepareStatement(totalPointsSQL);
            totalPointsStmt.setInt(1, submissionId);
            ResultSet totalPointsRs = totalPointsStmt.executeQuery();
            if (totalPointsRs.next()) {
                totalAvailablePoints = totalPointsRs.getInt("totalPoints");
            }
            totalPointsRs.close();
            totalPointsStmt.close();

            // 7. Forward to results page
            request.setAttribute("score", totalScore);
            request.setAttribute("totalPoints", totalAvailablePoints);
            request.setAttribute("submissionId", submissionId);
            request.getRequestDispatcher("quizResult.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            // close connection (optional cleanup block)
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
