package com.example.hospital_managment.EmailVerification;

public class EmailVerificationModel {

    private final String email;
    private final String code;

    public EmailVerificationModel(String email, String confirmationCode){
        this.email=email;
        this.code =confirmationCode;
    }

}
