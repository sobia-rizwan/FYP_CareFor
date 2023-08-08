package com.example.carefor;

public class CG_FAQModel {

    private String question;
    private String answer;
    private int course_rating;
    private int course_image;

    // Constructor
    public CG_FAQModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Getter and Setter

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }




}
