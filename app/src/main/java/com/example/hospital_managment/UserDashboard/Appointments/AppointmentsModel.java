package com.example.hospital_managment.UserDashboard.Appointments;

public class AppointmentsModel {

    private int id;
    private int doctorId;

    private int userId;

    private String localDate;

    private String localTime;
    private String appointemntStatus;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getAppointemntStatus() {
        return appointemntStatus;
    }

    public void setAppointemntStatus(String appointemntStatus) {
        this.appointemntStatus = appointemntStatus;
    }

    public int getId() {
        return id;
    }
}
