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

    public int insertSubmission(Submission submission) {
        String sql = "INSERT INTO submissions (user_id, quiz_id, submitted_at) VALUES (?, ?, NOW())";
        int submissionId = -1;

        if (submission == null) {
            System.err.println("Submission object is null.");
            return -1;
        }

        try (
                PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, submission.getUserId());
            stmt.setInt(2, submission.getQuizId());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted == 0) {
                System.err.println("Insert failed, no rows affected.");
                return -1;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    submissionId = generatedKeys.getInt(1);
                    System.out.println("Submission inserted with ID: " + submissionId);
                } else {
                    System.err.println("Insert succeeded but no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception during insertSubmission: " + e.getMessage());
            e.printStackTrace();
        }

        return submissionId;
    }

    public void insertAnswers(Answer answers) {
        String sql = "INSERT INTO answer (submission_id, question_id, selected_option_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, answers.getSubmissionId());
            stmt.setInt(2, answers.getQuestionId());
            stmt.setInt(3, answers.getSelectedOptionId());

            stmt.executeUpdate(); // Execute batch insert

            if (stmt != null) {
                stmt.close();
            }
                       
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
