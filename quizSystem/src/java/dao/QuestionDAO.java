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

    private Connection con;

    public QuestionDAO() {
        con = DBConnection.getConnection();
    }

    // Get options for a specific question
    public int insertQuestion(Question question) {
        int questionId = -1;

        String insertQuestionQuery = "INSERT INTO questions (quiz_id, question_text, type, points, order_index) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(insertQuestionQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, question.getQuizid());
            ps.setString(2, question.getQuestionText());
            ps.setString(3, question.getType());
            ps.setInt(4, question.getPoints());
            ps.setInt(5, question.getOrderIndex());

            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if (generatedKey.next()) {
                    questionId = generatedKey.getInt(1);
                }
                generatedKey.close();
            }
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return questionId;
    }

    // Get options for a specific question
    public boolean UpdateQuestion(Question question) {
        int questionId = -1;

        String UpdateQuestionquery = "UPDATE questions SET question_text = ?, points = ?, quiz_id = ? WHERE question_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(UpdateQuestionquery);

            ps.setString(1, question.getQuestionText());
            ps.setInt(2, question.getPoints());
            ps.setInt(3, question.getQuizid());
            ps.setInt(4, question.getQuestionID());
            
            int rowAffected = ps.executeUpdate();
            ps.close();

            return rowAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Get all questions by quizId
    public List<Question> getQuestionsByQuizId(int quizId) {

        List<Question> questions = new ArrayList<>();
        String selectIdQuery = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY order_index";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectIdQuery);
            preparedStatement.setInt(1, quizId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question();
                question.setQuestionID(resultSet.getInt("question_id"));
                question.setQuizid(resultSet.getInt("quiz_id"));
                question.setQuestionText(resultSet.getString("question_text"));
                question.setType(resultSet.getString("type"));
                question.setPoints(resultSet.getInt("points"));
                question.setOrderIndex(resultSet.getInt("order_index"));

                // Get options for this question
                OptionDAO optionDAO = new OptionDAO();
                List<Option> options = optionDAO.getOptionByid(question.getQuestionID());
                question.setOptions(options);

                questions.add(question);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return questions;
    }
}
// Create new question
//    public boolean createQuestion(Question question) {
//        try {
//            String sql = "INSERT INTO questions (quiz_id, question_text, type, points, order_index) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, question.getQuizId());
//            preparedStatement.setString(2, question.getQuestionText());
//            preparedStatement.setString(3, question.getType());
//            preparedStatement.setInt(4, question.getPoints());
//            preparedStatement.setInt(5, question.getOrderIndex());
//
//            int result = preparedStatement.executeUpdate();
//
//            // After question creation, insert the options for the question
//            for (Option option : question.getOptions()) {
//                String optionSql = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
//                PreparedStatement optionStmt = connection.prepareStatement(optionSql, Statement.RETURN_GENERATED_KEYS);
//                optionStmt.setInt(1, question.getQuestionId());
//                optionStmt.setString(2, option.getOptionText());
//                optionStmt.setBoolean(3, option.isCorrect());
//
//                optionStmt.executeUpdate();
//            }
//
//            return result > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//

