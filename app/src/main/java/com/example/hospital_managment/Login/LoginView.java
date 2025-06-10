package com.example.hospital_managment.Login;

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
import com.example.hospital_managment.R;
import com.example.hospital_managment.SignUp.SignUpView;

public class LoginView extends AppCompatActivity {
    private EditText emailText, passwordText;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });

        emailText = findViewById(R.id.nameEdit);
        passwordText = findViewById(R.id.editTextTextPassword);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginResult().observe(this, result ->
                showMessage("Login Result", result)
        );
    }

    public void logIn(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            showMessage("Error", "Please fill all the necessary fields");
        } else {
            loginViewModel.loginUser(email, password);
        }
    }

    public void signUp(View view){
        Intent intent = new Intent(LoginView.this, SignUpView.class);
        startActivity(intent);
    }
    public void showMessage(String title, String message) {
        if(message.contains("Please verify your email.")){
            SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", emailText.getText().toString());
            editor.apply();
            Intent intent = new Intent(LoginView.this, EmailVerificationView.class);
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