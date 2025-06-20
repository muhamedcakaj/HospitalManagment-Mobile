package com.example.hospital_managment.UserDashboard.CreateAppointment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hospital_managment.R;
import com.example.hospital_managment.UserDashboard.Chat.GetDoctorsDTO;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAppointmentView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAppointmentView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateAppointmentView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAppointmentView.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAppointmentView newInstance(String param1, String param2) {
        CreateAppointmentView fragment = new CreateAppointmentView();
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

    private Spinner doctorSpinner;
    private CreateAppointmentViewModel viewModel;
    private List<GetDoctorsDTO> doctorsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_appointment_view, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorSpinner = view.findViewById(R.id.doctorSpinner);
        viewModel = new ViewModelProvider(this).get(CreateAppointmentViewModel.class);

        viewModel.getDoctorsList().observe(getViewLifecycleOwner(), doctors -> {

            if (!doctors.isEmpty()) {
                doctorsList = doctors;
                List<String> displayList = new ArrayList<>();
                displayList.add("Select a Doctor");
                for (GetDoctorsDTO doc : doctors) {
                    displayList.add(doc.getFirst_name() + " " + doc.getLast_name() + " (" + doc.getSpecialization() + ")");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, displayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctorSpinner.setAdapter(adapter);
            } else {
                Toast.makeText(requireContext(), "No doctors found.", Toast.LENGTH_SHORT).show();
            }
        });

        doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    GetDoctorsDTO selectedDoctor = doctorsList.get(position - 1);
                    int doctorId = selectedDoctor.getId();
                    Toast.makeText(requireContext(), "Selected: " + doctorSpinner.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        viewModel.fetchDoctors(getContext());
    }
}