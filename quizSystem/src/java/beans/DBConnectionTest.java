/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author User
 */
import java.sql.Connection;

public class DBConnectionTest {
    public static void main(String[] args) {
        // Attempt to get a connection to the database
        Connection connection = DBConnection.getConnection();

        // Check if the connection was successful
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
