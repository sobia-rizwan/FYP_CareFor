package com.example.careforadmin;

public class DataClass {

    private String User_Name;
    private String Full_Name;
    private String CNIC;
    private String Gender;
    private String Email_Address;
    private String Experience;
    private String ClientGender;
    private String Description;
    private String GigType;
    private String Rate;
    private String Timings;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataClass(String user_Name, String full_Name, String CNIC, String gender, String email_Address, String experience, String clientGender, String description, String gigType, String rate, String timings) {
        this.User_Name = user_Name;
        this.Full_Name = full_Name;
        this.CNIC = CNIC;
        this.Gender = gender;
        this.Email_Address = email_Address;
        this.Experience = experience;
        this.ClientGender = clientGender;
        this.Description = description;
        this.GigType = gigType;
        this.Rate = rate;
        this.Timings = timings;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public String getCNIC() {
        return CNIC;
    }

    public String getGender() {
        return Gender;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public String getExperience() {
        return Experience;
    }

    public String getClientGender() {
        return ClientGender;
    }

    public String getDescription() {
        return Description;
    }

    public String getGigType() {
        return GigType;
    }

    public String getRate() {
        return Rate;
    }

    public String getTimings() {
        return Timings;
    }

    public DataClass(){

    }
}
