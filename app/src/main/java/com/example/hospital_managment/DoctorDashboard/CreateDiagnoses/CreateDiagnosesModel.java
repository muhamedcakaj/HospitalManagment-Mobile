package com.example.hospital_managment.DoctorDashboard.CreateDiagnoses;

public class CreateDiagnosesModel {
    private int doctorId;
    private String userEmail;
    private String diagnosis;

    public CreateDiagnosesModel(int doctorId,String userEmail,String diagnosis){
        this.doctorId=doctorId;
        this.userEmail=userEmail;
        this.diagnosis=diagnosis;
    }
}
