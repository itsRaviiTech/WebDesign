/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author User
 */
import beans.Quiz;
import beans.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    private Connection con;

    public StudentDAO() {
        con = DBConnection.getConnection(); // Connect to the database
    }

    // Method to retrieve quizzes for students
    public List<Quiz> getAvailableQuizzesForStudent() {
        List<Quiz> quizzes = new ArrayList<>();
        // Fetch quizzes that are published, without any filtering by teacher
        String selectQuizQuery = "SELECT "
                                + "quiz_id,"
                                + "title,"
                                + "description,"
                                + "DATE(created_at) AS created_date ,"
                                + "is_published "
                                + "FROM quizzes "
                                + "WHERE is_published = 1";  // Only fetch published quizzes

        try {
            PreparedStatement ps = con.prepareStatement(selectQuizQuery);
            ResultSet rs = ps.executeQuery();

            // Debugging: Print out the result size to check if quizzes are being fetched
        System.out.println("Fetching quizzes. Total quizzes found: " + quizzes.size());
        
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getInt("quiz_id"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setCreatedAt(rs.getDate("created_date").toLocalDate());
                quiz.setIsPublished(rs.getBoolean("is_published"));

                quizzes.add(quiz);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Debugging: Print number of quizzes returned
    System.out.println("Quizzes retrieved: " + quizzes.size());
        return quizzes;
    }
}
