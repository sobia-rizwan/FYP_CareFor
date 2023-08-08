package com.example.carefor;

public class BookingClass {

    private String Booking_Status;
    private String Caregiver_Name;
    private String Caregiver_Number;
    private String Caregiver_UserName;
    private String Client_Address;
    private String Client_FullName;
    private String Client_Gender;
    private String Client_UserName;
    private String Client_PhoneNumber;
    private String Date;
    private String Gig;
    private String Rate;
    private String Shift;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BookingClass(String booking_Status, String caregiver_Name, String caregiver_Number, String caregiver_UserName, String client_Address, String client_FullName, String client_Gender, String client_UserName, String client_PhoneNumber, String date, String gig, String rate, String shift) {
        this.Booking_Status = booking_Status;
        this.Caregiver_Name = caregiver_Name;
        this.Caregiver_Number = caregiver_Number;
        this.Caregiver_UserName = caregiver_UserName;
        this.Client_Address = client_Address;
        this.Client_FullName = client_FullName;
        this.Client_Gender = client_Gender;
        this.Client_UserName = client_UserName;
        this.Client_PhoneNumber = client_PhoneNumber;
        this.Date = date;
        this.Gig = gig;
        this.Rate = rate;
        this.Shift = shift;
    }

    public String getBooking_Status() {
        return Booking_Status;
    }

    public String getCaregiver_Name() {
        return Caregiver_Name;
    }

    public String getCaregiver_Number() {
        return Caregiver_Number;
    }

    public String getCaregiver_UserName() {
        return Caregiver_UserName;
    }

    public String getClient_PhoneNumber() {
        return Client_PhoneNumber;
    }

    public String getClient_Address() {
        return Client_Address;
    }

    public String getClient_FullName() {
        return Client_FullName;
    }

    public String getClient_Gender() {
        return Client_Gender;
    }

    public String getClient_UserName() {
        return Client_UserName;
    }

    public String getDate() {
        return Date;
    }

    public String getGig() {
        return Gig;
    }

    public String getRate() {
        return Rate;
    }

    public String getShift() {
        return Shift;
    }

    public BookingClass(){

    }
}
