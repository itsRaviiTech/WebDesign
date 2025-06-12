/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author User
 */
import java.util.ArrayList;
import java.util.List;

public class Question {
    
    private int quizid;
    private int questionID;
    private String questionText;
    private int points;
    private String type;
    private int orderIndex;
    private List<Option> options;

    public void setQuizid(int quizid) {
        this.quizid = quizid;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public int getQuizid() {
        return quizid;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getPoints() {
        return points;
    }

    public String getType() {
        return type;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public List<Option> getOptions() {
        return options;
    }
}  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    private int questionId;
//    private int quizId;
//    private String questionText;
//    private String type; // e.g., 'Multiple Choice', 'True/False', etc.
//    private int points;
//    private int orderIndex;
//    private List<Option> options; // List of options for this question
//
//    // Getters and Setters
//    public int getQuestionId() {
//        return questionId;
//    }
//    public void setQuestionId(int questionId) {
//        this.questionId = questionId;
//    }
//
//    public int getQuizId() {
//        return quizId;
//    }
//    public void setQuizId(int quizId) {
//        this.quizId = quizId;
//    }
//
//    public String getQuestionText() {
//        return questionText;
//    }
//    public void setQuestionText(String questionText) {
//        this.questionText = questionText;
//    }
//
//    public String getType() {
//        return type;
//    }
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public int getPoints() {
//        return points;
//    }
//    public void setPoints(int points) {
//        this.points = points;
//    }
//
//    public int getOrderIndex() {
//        return orderIndex;
//    }
//    public void setOrderIndex(int orderIndex) {
//        this.orderIndex = orderIndex;
//    }
//
//    public List<Option> getOptions() {
//        return options;
//    }
//    public void setOptions(List<Option> options) {
//        this.options = options;
//    }

   
//}