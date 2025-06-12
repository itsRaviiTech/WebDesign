/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author User
 */
import beans.Submission;
import beans.Answer;
import beans.Quiz;
import beans.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private Connection con;

    public StudentDAO() {
        con = DBConnection.getConnection(); // Connect to the database
    }

    // Method to retrieve quizzes for students
    public List<Quiz> getAvailableQuizzesForStudent() {
        List<Quiz> quizzes = new ArrayList<>();
        // Fetch quizzes that are published, without any filtering by teacher
        String selectQuizQuery = "SELECT "
                + "quiz_id,"
                + "title,"
                + "description,"
                + "DATE(created_at) AS created_date ,"
                + "is_published "
                + "FROM quizzes "
                + "WHERE is_published = 1";  // Only fetch published quizzes

        try {
            PreparedStatement ps = con.prepareStatement(selectQuizQuery);
            ResultSet rs = ps.executeQuery();

            // Debugging: Print out the result size to check if quizzes are being fetched
            System.out.println("Fetching quizzes. Total quizzes found: " + quizzes.size());

            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getInt("quiz_id"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setCreatedAt(rs.getDate("created_date").toLocalDate());
                quiz.setIsPublished(rs.getBoolean("is_published"));

                quizzes.add(quiz);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Debugging: Print number of quizzes returned
        System.out.println("Quizzes retrieved: " + quizzes.size());
        return quizzes;
    }

    public int insertSubmission(Submission submission) throws SQLException {
        String sql = "INSERT INTO submission (user_id, quiz_id, score, submitted_at) VALUES (?, ?, ?, NOW())";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int submissionId = -1;

        try {
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, submission.getUserId());
            stmt.setInt(2, submission.getQuizId());
            stmt.setFloat(3, submission.getScore()); // might be 0 at this stage

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    submissionId = rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return submissionId;
    }

    public void insertAnswers(List<Answer> answers) throws SQLException {
        String sql = "INSERT INTO answer (submission_id, question_id, selected_option_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);
            for (Answer ans : answers) {
                stmt.setInt(1, ans.getSubmissionId());
                stmt.setInt(2, ans.getQuestionId());
                stmt.setInt(3, ans.getSelectedOptionId());
                stmt.addBatch(); // Add to batch
            }
            stmt.executeBatch(); // Execute batch insert
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

}
