/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author User
 */
import beans.Question;
import beans.Option;
import beans.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private Connection connection;

    public QuestionDAO() {
        connection = DBConnection.getConnection();
    }

    // Create new question
    public boolean createQuestion(Question question) {
        try {
            String sql = "INSERT INTO questions (quiz_id, question_text, type, points, order_index) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, question.getQuizId());
            preparedStatement.setString(2, question.getQuestionText());
            preparedStatement.setString(3, question.getType());
            preparedStatement.setInt(4, question.getPoints());
            preparedStatement.setInt(5, question.getOrderIndex());

            int result = preparedStatement.executeUpdate();

            // After question creation, insert the options for the question
            for (Option option : question.getOptions()) {
                String optionSql = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
                PreparedStatement optionStmt = connection.prepareStatement(optionSql, Statement.RETURN_GENERATED_KEYS);
                optionStmt.setInt(1, question.getQuestionId());
                optionStmt.setString(2, option.getOptionText());
                optionStmt.setBoolean(3, option.isCorrect());

                optionStmt.executeUpdate();
            }

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all questions by quizId
    public List<Question> getQuestionsByQuizId(int quizId) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY order_index";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, quizId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Question question = new Question();
            question.setQuestionId(resultSet.getInt("question_id"));
            question.setQuizId(resultSet.getInt("quiz_id"));
            question.setQuestionText(resultSet.getString("question_text"));
            question.setType(resultSet.getString("type"));
            question.setPoints(resultSet.getInt("points"));
            question.setOrderIndex(resultSet.getInt("order_index"));

            // Get options for this question
            OptionDAO optionDAO = new OptionDAO();
            List<Option> options = optionDAO.getOptionsByQuestionId(question.getQuestionId());
            question.setOptions(options);

            questions.add(question);
        }

        return questions;
    }
}