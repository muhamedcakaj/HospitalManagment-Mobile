package com.example.hospital_managment.UserDashboard;

import android.os.Bundle;
import android.view.Menu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hospital_managment.UserDashboard.CreateAppointment.CreateAppointmentView;
import com.example.hospital_managment.UserDashboard.Appointments.AppointmentsView;
import com.example.hospital_managment.UserDashboard.Chat.ChatView;
import com.example.hospital_managment.UserDashboard.Diagnoses.DiagnosesView;
import com.example.hospital_managment.UserDashboard.Profile.ProfileView;
import com.example.hospital_managment.R;
import com.example.hospital_managment.databinding.ActivityUserDashboardBinding;

public class UserDashboard extends AppCompatActivity {

    private com.example.hospital_managment.databinding.ActivityUserDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityUserDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        binding.topAppBar.setTitle("Hello Patient");

        binding.topAppBar.setOnMenuItemClickListener(item -> {
            Fragment fragment=new ProfileView();
            loadFragment(fragment);
            return true;
        });

        if (savedInstanceState == null) {
            loadFragment(new CreateAppointmentView());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            if (id == R.id.nav_create_diagnoses) {
                fragment = new CreateAppointmentView();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_menu, menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }
}