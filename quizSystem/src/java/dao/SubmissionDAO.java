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
import beans.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubmissionDAO {
    private final Connection connection;

    public SubmissionDAO() {
        connection = DBConnection.getConnection();
    }

    // Create a new submission
    public boolean createSubmission(Submission submission) {
        try {
            String sql = "INSERT INTO submissions (user_id, quiz_id, score, submitted_at) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, submission.getUserId());
            preparedStatement.setInt(2, submission.getQuizId());
            preparedStatement.setFloat(3, submission.getScore());
            preparedStatement.setString(4, submission.getSubmittedAt());

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int submissionId = generatedKeys.getInt(1);
                    submission.setSubmissionId(submissionId);

                    // Save the answers
                    AnswerDAO answerDAO = new AnswerDAO();
                    for (Answer answer : submission.getAnswers()) {
                        answer.setSubmissionId(submissionId);
                        answerDAO.createAnswer(answer);
                    }

                    return true;
                }
            }
        } catch (SQLException e) {
        }
        return false;
    }

    // Get submissions by userId
    public List<Submission> getSubmissionsByUserId(int userId) throws SQLException {
        List<Submission> submissions = new ArrayList<>();
        String sql = "SELECT * FROM submissions WHERE user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Submission submission = new Submission();
            submission.setSubmissionId(resultSet.getInt("submission_id"));
            submission.setUserId(resultSet.getInt("user_id"));
            submission.setQuizId(resultSet.getInt("quiz_id"));
            submission.setScore(resultSet.getFloat("score"));
            submission.setSubmittedAt(resultSet.getString("submitted_at"));

            submissions.add(submission);
        }

        return submissions;
    }
    
    public List<Submission> getResultsByUser(int userId) throws SQLException {
        List<Submission> results = new ArrayList<>();
        String sql = "SELECT * FROM submissions WHERE user_id = ? ORDER BY submitted_at DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId); // Set userId in the query
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Submission submission = new Submission();
            submission.setSubmissionId(resultSet.getInt("submission_id"));
            submission.setUserId(resultSet.getInt("user_id"));
            submission.setQuizId(resultSet.getInt("quiz_id"));
            submission.setScore(resultSet.getFloat("score"));
            submission.setSubmittedAt(resultSet.getString("submitted_at"));
            results.add(submission);
        }
        return results;
    }
}