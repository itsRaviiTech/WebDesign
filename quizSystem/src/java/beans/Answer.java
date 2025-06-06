/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author User
 */
public class Answer {
    private int answerId;
    private int submissionId;
    private int questionId;
    private int selectedOptionId;
    private boolean isCorrect;

    // Getters and Setters
    public int getAnswerId() {
        return answerId;
    }
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getSubmissionId() {
        return submissionId;
    }
    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getSelectedOptionId() {
        return selectedOptionId;
    }
    public void setSelectedOptionId(int selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}