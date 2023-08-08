package com.example.carefor;

public class CG_HistoryModel {

    private String client_username;
    private String appointment_type;
    private String timing;
    private int appointment_rate;
    private int rating;

    public CG_HistoryModel(String client_username, String appointment_type, String timing, int appointment_rate, int rating) {
        this.client_username = client_username;
        this.appointment_type = appointment_type;
        this.timing = timing;
        this.appointment_rate = appointment_rate;
        this.rating = rating;
    }

    // Getter and Setter


    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String client_username) {
        this.client_username = client_username;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public int getAppointment_rate() {
        return appointment_rate;
    }

    public void setAppointment_rate(int appointment_rate) {
        this.appointment_rate = appointment_rate;
    }

    public int getRatings() {
        return rating;
    }

    public void setRatings(int rating) {
        this.rating = rating;
    }
}
