/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author User
 */
import beans.Option;
import beans.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDAO {
    private Connection connection;

    public OptionDAO() {
        connection = DBConnection.getConnection();
    }

    // Get options for a specific question
    public List<Option> getOptionsByQuestionId(int questionId) throws SQLException {
        List<Option> options = new ArrayList<>();
        String sql = "SELECT * FROM options WHERE question_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, questionId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Option option = new Option();
            option.setOptionId(resultSet.getInt("option_id"));
            option.setQuestionId(resultSet.getInt("question_id"));
            option.setOptionText(resultSet.getString("option_text"));
            option.setCorrect(resultSet.getBoolean("is_correct"));
            options.add(option);
        }

        return options;
    }
}