package com.example.hospital_managment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hospital_managment.DoctorDashboard.DoctorDashboard;
import com.example.hospital_managment.Login.LoginView;
import com.example.hospital_managment.SignUp.SignUpView;
import com.example.hospital_managment.Token.TokenManager;
import com.example.hospital_managment.UserDashboard.UserDashboard;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TokenManager tokenManager = new TokenManager(MainActivity.this);
        if (!(tokenManager.isTokenExpired())) {
            if(tokenManager.getRole().equals("Doctor")){
                Intent intent = new Intent(this, DoctorDashboard.class);
                startActivity(intent);
                finish();
                return;
            }else{
                Intent intent = new Intent(this, UserDashboard.class);
                startActivity(intent);
                finish();
                return;
            }

        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
        }
    }

    public void login(View view){
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
    }
    public void register(View view){
        Intent intent = new Intent (this, SignUpView.class);
        startActivity(intent);
    }
}