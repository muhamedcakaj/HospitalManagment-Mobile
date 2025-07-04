package com.example.hospital_managment.DoctorDashboard.Appointments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospital_managment.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentsView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentsView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppointmentsView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentsView.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentsView newInstance(String param1, String param2) {
        AppointmentsView fragment = new AppointmentsView();
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

    private AppointmentsViewModel appointmentsViewModel;

    private RecyclerView recyclerView;

    private AppointmentsAdapter diagnoseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_appointments_view, container, false);

       appointmentsViewModel=new ViewModelProvider(this).get(AppointmentsViewModel.class);
       recyclerView = view.findViewById(R.id.recyclerViewAppointments);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       diagnoseAdapter = new AppointmentsAdapter(new ArrayList<>(),requireContext(),appointmentsViewModel);

       recyclerView.setAdapter(diagnoseAdapter);

        appointmentsViewModel.getAppointments().observe(getViewLifecycleOwner(), appointments -> {
            diagnoseAdapter.updateList(appointments);
        });

        appointmentsViewModel.fetchDoctorAppointments(requireContext());

       return view;
    }
}