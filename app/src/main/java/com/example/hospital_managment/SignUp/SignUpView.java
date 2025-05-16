package com.example.hospital_managment.SignUp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.hospital_managment.EmailVerification.EmailVerificationView;
import com.example.hospital_managment.Login.LoginView;
import com.example.hospital_managment.R;

public class SignUpView extends AppCompatActivity {
    private EditText name,surname,email,password;

    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        name=findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signUpViewModel.getSingUpResult().observe(this, result ->
                showMessage("SignUp Result", result)
        );
    }
    public void register(View view) {
        String namee = name.getText().toString();
        String surnamee = surname.getText().toString();
        String emaill = email.getText().toString();
        String passwordd = password.getText().toString();

        if (namee.isEmpty() || surnamee.isEmpty()
                || emaill.isEmpty() || passwordd.isEmpty()) {
            showMessage("Error","Please fill all the necessary fields");
        }else{
            signUpViewModel.singUpUser(namee,surnamee,emaill,passwordd);
        }
    }
    public void showMessage(String title, String message) {
        if(message.contains("Please verify your email.")){
            SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email.getText().toString());
            editor.apply();
            Intent intent = new Intent(SignUpView.this, EmailVerificationView.class);
            startActivity(intent);
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}