package com.example.hospital_managment.UserDashboard.CreateAppointment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospital_managment.DoctorDashboard.Appointments.AppointmentsModel;
import com.example.hospital_managment.R;
import com.example.hospital_managment.UserDashboard.Chat.GetDoctorsDTO;
import android.widget.AdapterView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
    private TextView selectedDateText=null;
    private Calendar selectedDate;
    private String formattedDate;
    private GridLayout timeSlotsGrid;
    private final Set<String> busyTimes = new HashSet<>();
    private Integer selectedDoctorId = null;

    private Button bookAppointentButton;

    private String setTime = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_appointment_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedDateText = view.findViewById(R.id.selectedDateText);
        doctorSpinner = view.findViewById(R.id.doctorSpinner);
        timeSlotsGrid = view.findViewById(R.id.timeSlotsGrid);
        bookAppointentButton=view.findViewById(R.id.submitButton);

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

        viewModel.getAppointments().observe(getViewLifecycleOwner(), appointments -> {
            busyTimes.clear();

            for (AppointmentsModel appt : appointments) {
                if (formattedDate != null && formattedDate.equals(appt.getLocalDate())) {
                    String timeOnly = appt.getLocalTime();
                    if (timeOnly != null && timeOnly.length() >= 5) {
                        busyTimes.add(timeOnly.substring(0, 5));
                    }
                }
            }

            generateAndDisplayTimeSlots(); // Refresh UI after collecting busy times
        });

        doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    GetDoctorsDTO selectedDoctor = doctorsList.get(position - 1);
                    selectedDoctorId = selectedDoctor.getId();
                    if (formattedDate != null) {
                        viewModel.fetchDoctorAppointments(requireContext(), selectedDoctorId);
                        generateAndDisplayTimeSlots();
                    }
                } else {
                    selectedDoctorId = null;
                    timeSlotsGrid.removeAllViews(); // Clear time slots if no doctor is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bookAppointentButton.setOnClickListener(v->{
            if(selectedDoctorId != null&&selectedDateText!=null&&setTime!=null){
                selectedDoctorId=null;
                selectedDateText=null;
                Toast.makeText(requireContext(), "The Appointment Added Successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(requireContext(), "Please fill all the necessary fields", Toast.LENGTH_SHORT).show();
            }
        });

        selectedDateText.setOnClickListener(v -> showDatePicker());

        viewModel.fetchDoctors(getContext());


    }

    private void showDatePicker() {
        final Calendar today = Calendar.getInstance();

        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            selectedDate = Calendar.getInstance();
            selectedDate.set(year1, month1, dayOfMonth);

            formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year1, month1 + 1, dayOfMonth);
            selectedDateText.setText(formattedDate);

            if (selectedDoctorId != null) {
                viewModel.fetchDoctorAppointments(requireContext(), selectedDoctorId);
                generateAndDisplayTimeSlots();
            }

        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
        datePickerDialog.show();
    }

    private void generateAndDisplayTimeSlots() {
        timeSlotsGrid.removeAllViews(); // Clear previous time slots

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        try {
            Date startTime = sdf.parse("08:00:00");
            Date endTime = sdf.parse("15:30:00");

            calendar.setTime(startTime);

            while (!calendar.getTime().after(endTime)) {
                String timeSlot = sdf.format(calendar.getTime());
                addTimeSlotToGrid(timeSlot);
                calendar.add(Calendar.MINUTE, 30);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addTimeSlotToGrid(String time) {
        TextView timeSlotTextView = new TextView(requireContext());
        timeSlotTextView.setText(time);
        timeSlotTextView.setPadding(16, 16, 16, 16);
        timeSlotTextView.setGravity(Gravity.CENTER);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setMargins(8, 8, 8, 8);
        timeSlotTextView.setLayoutParams(params);

        if (busyTimes.contains(time)) {
            // Mark red and disable
            timeSlotTextView.setBackgroundColor(Color.parseColor("#FFCCCC")); // light red
            timeSlotTextView.setTextColor(Color.RED);
            timeSlotTextView.setEnabled(false);
        } else {
            // Available slot styling
            timeSlotTextView.setBackgroundResource(R.drawable.time_slot_background);
            timeSlotTextView.setTextColor(Color.BLACK);
            timeSlotTextView.setClickable(true);
            timeSlotTextView.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "Selected time: " + time, Toast.LENGTH_SHORT).show();
                setTime=time;
            });
        }

        timeSlotsGrid.addView(timeSlotTextView);
    }
}
