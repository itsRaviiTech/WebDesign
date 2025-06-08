/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.util.List;

/**
 *
 * @author User
 */
public class Quiz {
    private int quizId;
    private String title;
    private String description;
    private boolean isPublished;
    private int createdBy;
    private List<Question> questions;

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setQuestions(List questions) {
        this.questions = questions;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIsPublished() {
        return isPublished;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public List getQuestions() {
        return questions;
    }

    
}