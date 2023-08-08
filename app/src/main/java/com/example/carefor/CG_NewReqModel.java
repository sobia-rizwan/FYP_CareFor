package com.example.carefor;

import java.util.Date;

public class CG_NewReqModel {


    private String client_username;
    private String client_address;
    private String appointment_type;
    private String appointment_date;

    private int rating;
    private int course_image;

    // Constructor
    public CG_NewReqModel(String client_username, String client_address, String appointment_type, String appointment_date ) {
        this.client_username=client_username;
        this.client_address=client_address;
        this.appointment_type = appointment_type;
        this.appointment_date = appointment_date;
    }

    // Getter and Setter

    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String appointment_type) {
        this.client_username = appointment_type;
    }
    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }


    public String getAppointment() {
        return appointment_type;
    }

    public void setAppointment(String appointment_type) {
        this.appointment_type = appointment_type;
    }
    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }
}
