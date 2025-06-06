/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quiz.com;

import java.time.LocalTime;
import java.util.*;
/**
 *
 * @author HP
 */
public class Quiz {
    private int quizId;
    private String title;
    private String description;
    private String createdBy;
    private boolean isPublished;
    private LocalTime createdAt;

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public boolean getIsPublished() {
        return isPublished;
    }

    public LocalTime getCreatedAt() {
        return createdAt;
    }
    
    
}
