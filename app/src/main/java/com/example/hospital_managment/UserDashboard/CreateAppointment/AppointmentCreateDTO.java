package com.example.hospital_managment.UserDashboard.CreateAppointment;
public class AppointmentCreateDTO {
    private int doctorId;
    private int userId;
    private String localDate;
    private String localTime;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String date) {
        this.localDate = date;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String time) {
        this.localTime = time;
    }
}