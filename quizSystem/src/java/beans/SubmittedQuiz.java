/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.sql.Timestamp;
import java.util.Objects;

public class SubmittedQuiz {
    private int submissionId;
    private int quizId;
    private int studentId;
    private String studentName;
    private String quizTitle;
    private Timestamp submissionDate;

    // Getters and setters
    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Override
    public String toString() {
        return "SubmittedQuiz{" +
                "submissionId=" + submissionId +
                ", quizId=" + quizId +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", quizTitle='" + quizTitle + '\'' +
                ", submissionDate=" + submissionDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmittedQuiz that = (SubmittedQuiz) o;
        return submissionId == that.submissionId && quizId == that.quizId && studentId == that.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(submissionId, quizId, studentId);
    }
}
