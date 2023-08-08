package com.example.carefor;

public class CG_ReviewModel {

    private String appointment_type;
    private String feedback;
    private int rating;
    private int course_image;

    // Constructor
    public CG_ReviewModel(String appointment_type, int rating, String feedback) {
        this.appointment_type = appointment_type;
        this.rating = rating;
        this.feedback = feedback;
    }

    // Getter and Setter

    public String getAppointment() {
        return appointment_type;
    }

    public void setAppointment(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
