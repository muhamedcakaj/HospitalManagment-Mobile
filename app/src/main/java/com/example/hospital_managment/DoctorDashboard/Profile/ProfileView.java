package com.example.hospital_managment.DoctorDashboard.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospital_managment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileView.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileView newInstance(String param1, String param2) {
        ProfileView fragment = new ProfileView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    TextView nameSurnameView,gmailView;
    EditText nameEdit,surnameEdit,specializationView,descriptionView;
    ProfileViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        nameSurnameView = view.findViewById(R.id.nameSurnameView);
        gmailView=view.findViewById(R.id.gmailView);
        nameEdit=view.findViewById(R.id.nameEdit);
        surnameEdit=view.findViewById(R.id.surnameEdit);
        specializationView=view.findViewById(R.id.specializationView);
        descriptionView=view.findViewById(R.id.descriptionView);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.getProfileData().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null) {
                String fullName = profile.getFirst_name() + " " + profile.getLast_name();
                nameSurnameView.setText(fullName);

                nameEdit.setText(profile.getFirst_name());
                surnameEdit.setText(profile.getLast_name());
                specializationView.setText(profile.getSpecialization());
                descriptionView.setText(profile.getDescription());
            } else {
                Toast.makeText(getContext(), "Failed to load profile info", Toast.LENGTH_SHORT).show();
            }
        });

        // Trigger data fetch
        viewModel.fetchPersonalInfoFromDoctor(requireContext());

        return view;
    }

    }