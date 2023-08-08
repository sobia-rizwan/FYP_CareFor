package com.example.careforadmin;

public class ApprovalClass {
    private String User_Name;
    private String Full_Name;
    private String Status;
    private String CNIC;
    private String Address;
    private String Experience;
    private String Key;
    private String CNIC_Doc;
    private String Experience_Doc;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public ApprovalClass(String user_Name, String full_Name, String status, String CNIC, String address, String experience, String key, String cnic_doc, String experience_doc) {
        this.User_Name = user_Name;
        this.Full_Name = full_Name;
        this.Status = status;
        this.CNIC = CNIC;
        this.Address = address;
        this.Experience = experience;
        this.Key = key;
        this.CNIC_Doc = cnic_doc;
        this.Experience_Doc = experience_doc;
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

    public String getAddress() {
        return Address;
    }

    public String getExperience() {
        return Experience;
    }

    public String getStatus() {
        return Status;
    }

    public String getCNIC_Doc() {
        return CNIC_Doc;
    }

    public String getExperience_Doc() {
        return Experience_Doc;
    }

    public ApprovalClass(){

    }
}
