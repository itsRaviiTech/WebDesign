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

    private final Connection con;

    public OptionDAO() {
        con = DBConnection.getConnection();
    }

<<<<<<< Updated upstream
    public boolean insertOptions(Option option, int questionId) {
        int status= -1;
        String sql = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, questionId);
            ps.setString(2, option.getOptionText());
            ps.setBoolean(3, option.getIsCorrect());

            status = ps.executeUpdate();
            ps.close();
            
            return status > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
=======
    // Insert a new option for a specific question
    public void insertOptions(Option option, int questionId) {
        String sql = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
        try {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, questionId);
                ps.setString(2, option.getOptionText());
                ps.setBoolean(3, option.getIsCorrect());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
>>>>>>> Stashed changes
        }
    }

    // Update an existing option by its optionId
    public boolean UpdateOptionsByOptionID(Option option) {
        String sql = "UPDATE options SET option_text = ?, is_correct = ? WHERE option_id = ?";
        try {
            int rowsAffected;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, option.getOptionText());
                ps.setBoolean(2, option.getIsCorrect());
                ps.setInt(3, option.getOptionID());
                rowsAffected = ps.executeUpdate();
            }
            return rowsAffected > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    // Get all options for a specific question by its questionId
    public List<Option> getOptionByQuestionId(int questionID) {
        List<Option> optionsList = new ArrayList<>();
        String selectOptionQuery = "SELECT * FROM options WHERE question_id = ?";
        try {
            try (PreparedStatement ps = con.prepareStatement(selectOptionQuery)) {
                ps.setInt(1, questionID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Option option = new Option();
                    option.setOptionID(rs.getInt("option_id"));
                    option.setQuestionID(rs.getInt("question_id"));
                    option.setOptionText(rs.getString("option_text"));
                    option.setIsCorrect(rs.getBoolean("is_correct"));
                    optionsList.add(option);
                }
            }
        } catch (SQLException ex) {
        }
        return optionsList;
    }

    // Get a single option by its optionId
    public Option getOptionById(int optionID) {
        Option option = null;
        String selectOptionQuery = "SELECT * FROM options WHERE option_id = ?";
        try {
            try (PreparedStatement ps = con.prepareStatement(selectOptionQuery)) {
                ps.setInt(1, optionID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    option = new Option();
                    option.setOptionID(rs.getInt("option_id"));
                    option.setQuestionID(rs.getInt("question_id"));
                    option.setOptionText(rs.getString("option_text"));
                    option.setIsCorrect(rs.getBoolean("is_correct"));
                }
            }
        } catch (SQLException ex) {
        }
        return option;
    }

    // Get the correct option for a given question ID
    public Option getCorrectOptionByQuestionId(int questionID) {
        Option correctOption = null;
        String query = "SELECT * FROM options WHERE question_id = ? AND is_correct = true";
        try {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, questionID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    correctOption = new Option();
                    correctOption.setOptionID(rs.getInt("option_id"));
                    correctOption.setQuestionID(rs.getInt("question_id"));
                    correctOption.setOptionText(rs.getString("option_text"));
                    correctOption.setIsCorrect(rs.getBoolean("is_correct"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return correctOption;
    }

    // Get all options for a specific quiz
    public List<Option> getOptionByQuizID(int quizID) {
        List<Option> optionList = new ArrayList<>();
        String selectQuery = "SELECT qs.question_id, op.option_id, op.option_text, op.is_correct " +
                             "FROM options op " +
                             "JOIN questions qs USING(question_id) " +
                             "JOIN quizzes qz USING(quiz_id) " +
                             "WHERE quiz_id = ?";
        try {
            try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
                ps.setInt(1, quizID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Option option = new Option();
                    option.setQuestionID(rs.getInt("qs.question_id"));
                    option.setOptionID(rs.getInt("op.option_id"));
                    option.setOptionText(rs.getString("op.option_text"));
                    option.setIsCorrect(rs.getBoolean("op.is_correct"));
                    optionList.add(option);
                }
            }
        } catch (SQLException ex) {
        }
        return optionList;
    }
    
    public Boolean deleteOptionByQuestionID( int questionId){
        int status = -1;
        String deleteQuery = "DELETE FROM options WHERE question_id = ?";
        
        try{
            
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setInt(1, questionId);
            
            status = ps.executeUpdate();
            return status > 0; 
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
