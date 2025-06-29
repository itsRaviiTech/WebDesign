/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author User
 */
public class Option {

    private int optionID;
    private int questionID;
    private String optionText;
    private boolean isCorrect;
    private boolean isSelected;

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setOptionID(int optionID) {
        this.optionID = optionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getOptionID() {
        return optionID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getOptionText() {
        return optionText;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

}
