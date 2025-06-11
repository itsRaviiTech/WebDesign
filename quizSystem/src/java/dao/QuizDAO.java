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
import java.time.LocalDate;

public class QuizDAO {

    private Connection con;

    public QuizDAO() {
        con = DBConnection.getConnection();
    }

    public int createQuiz(Quiz quiz) {

        int id = -1;
        try {
            String sql = "INSERT INTO quizzes (title, description, is_published, created_by) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setBoolean(3, quiz.isIsPublished());
            preparedStatement.setInt(4, quiz.getCreatedBy());

            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean deleteQuizAndRelatedData(int quizId) {
        String deleteAnswersQuery = "DELETE a FROM answer a "
                + "JOIN submissions s ON a.submission_id = s.submission_id "
                + "WHERE s.quiz_id = ?";

        String deleteSubmissionsQuery = "DELETE FROM submissions WHERE quiz_id = ?";

        String deleteOptionsQuery = "DELETE o FROM options o "
                + "JOIN questions q ON o.question_id = q.question_id "
                + "WHERE q.quiz_id = ?";

        String deleteQuestionsQuery = "DELETE FROM questions WHERE quiz_id = ?";
        String deleteQuizQuery = "DELETE FROM quizzes WHERE quiz_id = ?";

        boolean success = false;

        try {
            con.setAutoCommit(false);

            try {
                PreparedStatement psDeleteAnswers = con.prepareStatement(deleteAnswersQuery);
                PreparedStatement psDeleteSubmissions = con.prepareStatement(deleteSubmissionsQuery);
                PreparedStatement psDeleteOptions = con.prepareStatement(deleteOptionsQuery);
                PreparedStatement psDeleteQuestions = con.prepareStatement(deleteQuestionsQuery);
                PreparedStatement psDeleteQuiz = con.prepareStatement(deleteQuizQuery);

                // Delete answers linked to submissions of this quiz
                psDeleteAnswers.setInt(1, quizId);
                psDeleteAnswers.executeUpdate();

                // Delete submissions of this quiz
                psDeleteSubmissions.setInt(1, quizId);
                psDeleteSubmissions.executeUpdate();

                // Delete options of this quiz's questions
                psDeleteOptions.setInt(1, quizId);
                psDeleteOptions.executeUpdate();

                // Delete questions of the quiz
                psDeleteQuestions.setInt(1, quizId);
                psDeleteQuestions.executeUpdate();

                // Delete the quiz itself
                int rowsAffected = 0;
                psDeleteQuiz.setInt(1, quizId);
                rowsAffected = psDeleteQuiz.executeUpdate();

                con.commit();
                success = (rowsAffected > 0);
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public void assignQuizToStudents(String quizId, String[] studentIds) {
        String sql = "INSERT INTO quiz_assignments (quiz_id, student_id) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (String studentId : studentIds) {
                preparedStatement.setString(1, quizId);
                preparedStatement.setString(2, studentId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
        }
    }

    public List<Quiz> getQuizzesByUserID(int userId) {
        List<Quiz> quizzes = new ArrayList<>();

        String selectQuizQuery = "SELECT \n"
                + "quiz_id,\n"
                + "title,\n"
                + "description,\n"
                + "DATE(created_at) AS created_date ,\n"
                + "is_published\n"
                + "FROM quizzes \n"
                + "WHERE created_by = ?";

        try {
            PreparedStatement ps = con.prepareStatement(selectQuizQuery);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return quizzes;
    }

    // Method to fetch all quizzes
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes WHERE is_published = 1";

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(resultSet.getInt("quiz_id"));
                quiz.setTitle(resultSet.getString("title"));
                quiz.setDescription(resultSet.getString("description"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
        }
        return quizzes;
    }

    public Quiz getQuizById(int quizId) {
        Quiz quiz = null;
        String sql = "SELECT * FROM quizzes WHERE quiz_id = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, quizId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                quiz = new Quiz();
                quiz.setQuizId(resultSet.getInt("quiz_id"));
                quiz.setTitle(resultSet.getString("title"));
                quiz.setDescription(resultSet.getString("description"));
                quiz.setIsPublished(resultSet.getBoolean("is_published"));
            }
        } catch (SQLException e) {
        }

        return quiz;
    }

    public int UpdateQuiz(Quiz quiz) {
        int id = -1;
        try {
            String sql = "UPDATE quizzes SET title = ?, description = ?, is_published = ? WHERE quiz_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setBoolean(3, quiz.isIsPublished());
            preparedStatement.setInt(4, quiz.getQuizId());

            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                id = quiz.getQuizId();  // Return the existing quizId since update succeeded
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
