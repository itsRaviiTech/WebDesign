/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import beans.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kirtie
 */
public class QuizAssignmentsDAO {

    public boolean isMultipleAttemptsAllowed(int userId, int quizId) {
        String sql = "SELECT allow_multiple_attempts FROM quiz_assignments WHERE user_id = ? AND quiz_id = ?";
        
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, quizId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("allow_multiple_attempts") == 1;  // Checks if attempts are allowed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Default to not allowing reattempts
    }
}
