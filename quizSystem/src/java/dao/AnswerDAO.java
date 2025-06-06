/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author User
 */
import beans.Answer;
import beans.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    private Connection connection;

    public AnswerDAO() {
        connection = DBConnection.getConnection();
    }

    // Create an answer for a submission
    public boolean createAnswer(Answer answer) {
        try {
            String sql = "INSERT INTO answer (submission_id, question_id, selected_option_id, is_correct) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, answer.getSubmissionId());
            preparedStatement.setInt(2, answer.getQuestionId());
            preparedStatement.setInt(3, answer.getSelectedOptionId());
            preparedStatement.setBoolean(4, answer.isCorrect());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        }
        return false;
    }

    // Get answers by submission ID
    public List<Answer> getAnswersBySubmissionId(int submissionId) throws SQLException {
        List<Answer> answers = new ArrayList<>();
        String sql = "SELECT * FROM answer WHERE submission_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, submissionId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Answer answer = new Answer();
            answer.setAnswerId(resultSet.getInt("answer_id"));
            answer.setSubmissionId(resultSet.getInt("submission_id"));
            answer.setQuestionId(resultSet.getInt("question_id"));
            answer.setSelectedOptionId(resultSet.getInt("selected_option_id"));
            answer.setCorrect(resultSet.getBoolean("is_correct"));
            answers.add(answer);
        }

        return answers;
    }
}