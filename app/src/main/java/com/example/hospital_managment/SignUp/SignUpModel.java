package com.example.hospital_managment.SignUp;

public class SignUpModel {
    private final String firstName;
    private final String lastName;

    private final String email;

    private final String password;

    public SignUpModel(String firstName,String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
