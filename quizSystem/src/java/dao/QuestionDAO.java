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
import dao.OptionDAO;
import beans.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    private final Connection con;

    public QuestionDAO() {
        con = DBConnection.getConnection();
    }

    // Get options for a specific question
    public int insertQuestion(Question question) {
        int questionId = -1;

        String insertQuestionQuery = "INSERT INTO questions (quiz_id, question_text, type, points, order_index) VALUES (?, ?, ?, ?, ?)";
        try {
            try (PreparedStatement ps = con.prepareStatement(insertQuestionQuery, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, question.getQuizid());
                ps.setString(2, question.getQuestionText());
                ps.setString(3, question.getType());
                ps.setInt(4, question.getPoints());
                ps.setInt(5, question.getOrderIndex());
                
                int rowAffected = ps.executeUpdate();
                
                if (rowAffected > 0) {
                    try (ResultSet generatedKey = ps.getGeneratedKeys()) {
                        if (generatedKey.next()) {
                            questionId = generatedKey.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
        }

        return questionId;
    }

    // Get options for a specific question
    public boolean UpdateQuestion(Question question) {
<<<<<<< Updated upstream

        String UpdateQuestionquery = "UPDATE questions SET question_text = ?, points = ?, quiz_id = ? WHERE question_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(UpdateQuestionquery);

=======
        String updateQuestionQuery = "UPDATE questions SET question_text = ?, points = ?, quiz_id = ?, order_index = ? WHERE question_id = ?";
        try (PreparedStatement ps = con.prepareStatement(updateQuestionQuery)) {
>>>>>>> Stashed changes
            ps.setString(1, question.getQuestionText());
            ps.setInt(2, question.getPoints());
            ps.setInt(3, question.getQuizid());
            ps.setInt(4, question.getOrderIndex());
            ps.setInt(5, question.getQuestionID());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            return false;
        }
    }


    // Get all questions by quizId
    public List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> questions = new ArrayList<>();
        String selectQuestionsQuery = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY order_index";

        try (PreparedStatement ps = con.prepareStatement(selectQuestionsQuery)) {
            ps.setInt(1, quizId);
            ResultSet resultSet = ps.executeQuery();

            // Iterate through each question and retrieve associated options
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
                List<Option> options = optionDAO.getOptionByQuestionId(question.getQuestionID());
                question.setOptions(options);

                // Add the question with options to the list
                questions.add(question);
            }
        } catch (SQLException ex) {
        }
        return questions;
    }
    
    public boolean deleteQuestionbyId(int id){
        int status = -1;
        boolean optionStatus;
        String deleteQuery = "DELETE FROM questions WHERE question_id = ?";
        try{
            
            OptionDAO optionDao = new OptionDAO();
            optionStatus = optionDao.deleteOptionByQuestionID(id);
            
            if( !optionStatus){
                return false;
            }
            
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setInt(1, id);
            status = ps.executeUpdate();
            
            
            ps.close();
            return status > 0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

