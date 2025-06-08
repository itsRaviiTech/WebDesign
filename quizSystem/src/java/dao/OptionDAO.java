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
}
