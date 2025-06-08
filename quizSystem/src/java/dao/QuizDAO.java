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
import com.mysql.cj.xdevapi.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private Connection connection;

    public QuizDAO() {
        connection = DBConnection.getConnection();
    }

    public int createQuiz(Quiz quiz) {
        
        int id = -1;
        try {
            String sql = "INSERT INTO quizzes (title, description, is_published, created_by) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setBoolean(3, quiz.isIsPublished());
            preparedStatement.setInt(4, quiz.getCreatedBy());
            
            int rowAffected = preparedStatement.executeUpdate();
            
            if( rowAffected > 0){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    id = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean deleteQuiz(int quizId) {
        try {
            String sql = "DELETE FROM quizzes WHERE quiz_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, quizId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        }
        return false;
    }
    
    public void assignQuizToStudents(String quizId, String[] studentIds) {
        String sql = "INSERT INTO quiz_assignments (quiz_id, student_id) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (String studentId : studentIds) {
                preparedStatement.setString(1, quizId);
                preparedStatement.setString(2, studentId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
        }
    }

    // Method to fetch all quizzes
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(resultSet.getInt("id"));
                quiz.setTitle(resultSet.getString("title"));
                quiz.setDescription(resultSet.getString("description"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
        }
        return quizzes;
    }
    
    public Quiz getQuizById(int quizId) {  // Change method parameter to int
        Quiz quiz = null;
        String sql = "SELECT * FROM quizzes WHERE id = ?";  // SQL query to get a quiz by ID

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, quizId);  // Correct: passing quizId as int
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                quiz = new Quiz();
                quiz.setQuizId(resultSet.getInt("id"));
                quiz.setTitle(resultSet.getString("title"));
                quiz.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
        }

        return quiz;
    }

}