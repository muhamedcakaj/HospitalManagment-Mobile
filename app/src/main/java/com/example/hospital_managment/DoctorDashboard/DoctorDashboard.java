package com.example.hospital_managment.DoctorDashboard;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hospital_managment.DoctorDashboard.Appointments.AppointmentsView;
import com.example.hospital_managment.DoctorDashboard.Chat.ChatView;
import com.example.hospital_managment.DoctorDashboard.CreateDiagnoses.CreateDiagnosesView;
import com.example.hospital_managment.DoctorDashboard.Diagnoses.DiagnosesView;
import com.example.hospital_managment.R;
import com.example.hospital_managment.databinding.ActivityDoctorDashboardBinding;

public class DoctorDashboard extends AppCompatActivity {

    private com.example.hospital_managment.databinding.ActivityDoctorDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityDoctorDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Show system UI

        // Set top app bar
        setSupportActionBar(binding.topAppBar);

        // Top app bar menu click
        binding.topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_profile) {
                Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new CreateDiagnosesView());
        }

        // Handle bottom navigation item clicks
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            if (id == R.id.nav_create_diagnoses) {
                fragment = new CreateDiagnosesView();
            } else if (id == R.id.nav_appointments) {
                fragment = new AppointmentsView();
            } else if (id == R.id.nav_chat) {
                fragment = new ChatView();
            } else if (id == R.id.nav_diagnoses) {
                fragment = new DiagnosesView();
            } else {
                return false;
            }

            loadFragment(fragment);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }
}