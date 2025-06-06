package DAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.*;
import java.sql.*;
import Quiz.com.Quiz;

/**
 *
 * @author HP
 */
public class LectureDAO {

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/attendance", "root", "admin");
        } catch (Exception e) {
            System.out.println(e);
        }
        // return the connection object
        return con;
    }

    public static int addQuestion(Quiz qObj){

        Connection con = null;
        PreparedStatement ps = null;
        int status =0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/your_db", "username", "password");

            String addQuizQuery = "INSERT INTO quizzes (title, description, created_by, created_at, is_published) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(addQuizQuery);
            ps.setString(1, qObj.getTitle());
            ps.setString(2, qObj.getDescription());
            ps.setString(3, qObj.getCreatedBy());
            ps.setTime(4, Time.valueOf(qObj.getCreatedAt()));
            ps.setBoolean(5, qObj.getIsPublished());
            
            status = ps.executeUpdate();
            
            con.close();
        }catch ( Exception ex){
            ex.printStackTrace();
        }
        return status;   
    }
    
    
}
