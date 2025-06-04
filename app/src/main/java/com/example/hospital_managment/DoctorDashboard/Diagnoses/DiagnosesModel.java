package com.example.hospital_managment.DoctorDashboard.Diagnoses;

import java.time.LocalDateTime;

public class DiagnosesModel {
    private int id;

    private int doctorId;

    private int userId;

    private String diagnosis;

    private String  diagnosis_date;

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

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis_date() {
        return diagnosis_date;
    }

    public void setDiagnosis_date(String diagnosis_date) {
        this.diagnosis_date = diagnosis_date;
    }
}
