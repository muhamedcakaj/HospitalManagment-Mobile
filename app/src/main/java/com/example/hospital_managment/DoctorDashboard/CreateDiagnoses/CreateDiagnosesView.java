package com.example.hospital_managment.DoctorDashboard.CreateDiagnoses;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.hospital_managment.R;

public class CreateDiagnosesView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateDiagnosesView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateDiagnosesView.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateDiagnosesView newInstance(String param1, String param2) {
        CreateDiagnosesView fragment = new CreateDiagnosesView();
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
    private EditText patientEmail,diagnoseDescription;
    private Button submitButton;
    private CreateDiagnosesViewModel viewModel;

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_diagnoses_view, container, false);


        patientEmail=view.findViewById(R.id.patientEmail);
        diagnoseDescription=view.findViewById(R.id.diagnoseDescription);
        submitButton=view.findViewById(R.id.submitButton);
        viewModel = new ViewModelProvider(this).get(CreateDiagnosesViewModel.class);

        viewModel.getDiagnosesResult().observe(getViewLifecycleOwner(), result ->
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show());

        submitButton.setOnClickListener(v -> {
            String email = patientEmail.getText().toString();
            String description = diagnoseDescription.getText().toString();

            if(email.isEmpty()||description.isEmpty()){
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }else{
                viewModel.createDiagnosis(requireContext(),email,description);
                patientEmail.setText("");
                diagnoseDescription.setText("");
            }
        });

        return view;

    }
}