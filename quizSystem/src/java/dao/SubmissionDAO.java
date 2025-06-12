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
    public List<Submission> getSubmissionsForTeacher(int teacherId) throws SQLException {
        List<Submission> submissions = new ArrayList<>();

        String sql = "SELECT s.submission_id, s.user_id, s.quiz_id, s.score, s.submitted_at, "
                + "u.name AS student_name, "
                + "q.title AS quiz_title "
                + "FROM submissions s "
                + "JOIN quizzes q ON s.quiz_id = q.quiz_id "
                + "JOIN users u ON s.user_id = u.user_id "
                + "WHERE q.created_by = ? "
                + "ORDER BY s.submitted_at DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Submission submission = new Submission();
                submission.setSubmissionId(rs.getInt("submission_id"));
                submission.setUserId(rs.getInt("user_id"));
                submission.setQuizId(rs.getInt("quiz_id"));
                submission.setScore(rs.getFloat("score"));
                submission.setSubmittedAt(rs.getString("submitted_at"));
                submission.setStudentName(rs.getString("student_name"));
                submission.setQuizTitle(rs.getString("quiz_title"));

                submissions.add(submission);
            }
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
