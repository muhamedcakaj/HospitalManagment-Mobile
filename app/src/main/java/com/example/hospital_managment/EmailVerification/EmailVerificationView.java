package com.example.hospital_managment.EmailVerification;

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

import com.example.hospital_managment.DoctorDashboard.DoctorDashboard;
import com.example.hospital_managment.R;
import com.example.hospital_managment.UserDashboard.UserDashboard;

public class EmailVerificationView extends AppCompatActivity {
    private EditText verificationCode;
    private EmailVerificationViewModel verificationViewModel;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_verification_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });

        verificationCode = findViewById(R.id.VerificaitonCode);
        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        verificationViewModel = new ViewModelProvider(this).get(EmailVerificationViewModel.class);

        verificationViewModel.getLoginResult().observe(this, result ->
                showMessage("Login Result", result)
        );
    }
    public void verify(View view){
        String code = verificationCode.getText().toString();
        if(code.isEmpty()){
            showMessage("Error","Please fill the necessary fields");
        }else{
            String email = prefs.getString("email", null);
            verificationViewModel.emailVerify(email,code,this);
        }
    }
    public void showMessage(String title,String message){
        if(message.equals("Doctor")){
            Intent intent = new Intent(this, DoctorDashboard.class);
            startActivity(intent);
        }
        else if(message.equals("User")){
            Intent intent = new Intent(this, UserDashboard.class);
            startActivity(intent);
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(true)
                    .show();
        }
    }
}