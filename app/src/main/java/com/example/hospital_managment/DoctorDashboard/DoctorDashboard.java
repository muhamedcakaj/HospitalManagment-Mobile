package com.example.hospital_managment.DoctorDashboard;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hospital_managment.DoctorDashboard.Appointments.AppointmentsView;
import com.example.hospital_managment.DoctorDashboard.Chat.ChatView;
import com.example.hospital_managment.DoctorDashboard.Diagnoses.DiagnosesView;
import com.example.hospital_managment.R;
import com.example.hospital_managment.databinding.ActivityDoctorDashboardBinding;

public class DoctorDashboard extends AppCompatActivity {

    private ActivityDoctorDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use ViewBinding
        binding = ActivityDoctorDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up top bar
        setSupportActionBar(binding.topAppBar);

        // Handle profile icon click
        binding.topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_profile) {
                // TODO: Navigate to Profile Fragment
                Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Set default fragment


        // Handle bottom nav item selection
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_diagnoses) {
                selectedFragment = new DiagnosesView();
            } else if (itemId == R.id.nav_appointments) {
                selectedFragment = new AppointmentsView();
            } else if (itemId == R.id.nav_chat) {
                selectedFragment = new ChatView();
            } else {
                selectedFragment = new ChatView();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, selectedFragment)
                    .commit();
            return true;
        });
    }
}