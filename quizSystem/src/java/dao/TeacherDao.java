/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import beans.QuizStats;
import beans.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ravib
 */
public class TeacherDao {

    private final Connection connection;

    public TeacherDao() {
        connection = DBConnection.getConnection();
    }

// Get total submissions received by quizzes created by this teacher
    public int getTotalSubmissionsByTeacher(int teacherId) {
        int total = 0;
        try {
            String sql = "SELECT COUNT(*) FROM submissions WHERE quiz_id IN (SELECT quiz_id FROM quizzes WHERE created_by = ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Get average score per quiz created by the teacher
    public List<QuizStats> getAverageScoresPerQuiz(int teacherId) {
        List<QuizStats> statsList = new ArrayList<>();

        try {
            String sql = "SELECT q.title AS quiz_title, AVG(s.score) AS average_score "
                    + "FROM quizzes q "
                    + "JOIN submissions s ON q.quiz_id = s.quiz_id "
                    + "WHERE q.created_by = ? "
                    + "GROUP BY q.quiz_id, q.title";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                QuizStats stat = new QuizStats();
                stat.setQuizTitle(rs.getString("quiz_title"));
                stat.setAverageScore(rs.getDouble("average_score"));
                statsList.add(stat);
                System.out.println("Fetched average for quiz: " + stat.getQuizTitle() + " -> " + stat.getAverageScore());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return statsList;
    }

}
