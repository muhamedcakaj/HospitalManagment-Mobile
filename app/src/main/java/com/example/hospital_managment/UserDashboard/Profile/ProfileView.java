package com.example.hospital_managment.UserDashboard.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospital_managment.R;
import com.example.hospital_managment.Token.TokenManager;

import org.json.JSONObject;

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
    EditText nameEdit,surnameEdit;
    ProfileViewModel viewModel;
    Button savePatientPersonalInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_profile_view2, container, false);
       nameSurnameView = view.findViewById(R.id.nameSurnameView);
       gmailView=view.findViewById(R.id.gmailView);
       nameEdit=view.findViewById(R.id.nameEdit);
       surnameEdit=view.findViewById(R.id.surnameEdit);
       savePatientPersonalInfo=view.findViewById(R.id.savePatientPersonalInfo);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.getProfileDataPatient().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null) {
                String fullName = profile.getFirst_name()+ " " + profile.getSecond_name();
                gmailView.setText(extractEmailFromToken());
                nameSurnameView.setText(fullName);
                nameEdit.setText(profile.getFirst_name());
                surnameEdit.setText(profile.getSecond_name());
            } else {
                Toast.makeText(getContext(), "Failed to load profile info", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.fetchPersonalInfoFromPatient(requireContext());

        savePatientPersonalInfo.setOnClickListener(v-> {
            String name = nameEdit.getText().toString();
            String surname=surnameEdit.getText().toString();
            if(name.isEmpty()||surname.isEmpty()){
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }else{
                viewModel.updateDoctorPersonalInfo(name,surname,requireContext());
                Toast.makeText(getContext(), "Personal information updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    public String extractEmailFromToken() {
        try {
            TokenManager tokenManager = new TokenManager(requireContext());
            String token = tokenManager.getAccessToken();
            String[] parts = token.split("\\.");
            if (parts.length != 3) return null;

            String payloadJson = new String(Base64.decode(parts[1], Base64.URL_SAFE), "UTF-8");
            JSONObject payload = new JSONObject(payloadJson);
            return payload.optString("email");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}