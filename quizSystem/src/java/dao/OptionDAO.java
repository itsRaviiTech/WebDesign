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
import beans.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDAO {

    private Connection con;

    public OptionDAO() {
        con = DBConnection.getConnection();
    }

    public void insertOptions(Option option, int questionId) {
        String sql = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, questionId);
            ps.setString(2, option.getOptionText());
            ps.setBoolean(3, option.getIsCorrect());

            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean UpdateOptionsByOptionID(Option option) {

        String sql = "UPDATE options SET option_text = ?, is_correct = ?  WHERE option_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, option.getOptionText());
            ps.setBoolean(2, option.getIsCorrect());
            ps.setInt(3, option.getOptionID());

            int rowsAffected = ps.executeUpdate();
            ps.close();

            return rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Option> getOptionByid(int QuestionID) {

        List<Option> optionsList = new ArrayList<>();
        String selectOptionQuery = "SELECT * FROM `options` WHERE question_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(selectOptionQuery);
            ps.setInt(1, QuestionID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Option option = new Option();

                option.setOptionID(rs.getInt("option_id"));
                option.setQuestionID(rs.getInt("question_id"));
                option.setOptionText(rs.getString("option_text"));
                option.setIsCorrect(rs.getBoolean("is_correct"));

                optionsList.add(option);
            }
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return optionsList;
    }

    public List<Option> getOptionByQuizID(int QuizID) {

        List<Option> optionList = new ArrayList<>();
        String selectQuery = "SELECT qs.question_id, op.option_id, op.option_text, op.is_correct\n"
                + "FROM options op\n"
                + "JOIN questions qs\n"
                + "USING(question_id)\n"
                + "JOIN quizzes qz\n"
                + "USING(quiz_id)\n"
                + "WHERE quiz_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(selectQuery);
            ps.setInt(1, QuizID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Option option = new Option();
                option.setQuestionID(rs.getInt("qs.question_id"));
                option.setOptionID(rs.getInt("op.option_id"));
                option.setOptionText(rs.getString("op.option_text"));
                option.setIsCorrect(rs.getBoolean("op.is_correct"));

                optionList.add(option);
            }
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionList;
    }
}
