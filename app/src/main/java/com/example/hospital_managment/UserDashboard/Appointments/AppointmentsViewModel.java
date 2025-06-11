package com.example.hospital_managment.UserDashboard.Appointments;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.GetIdFromToken;
import com.example.hospital_managment.Token.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsViewModel extends ViewModel {
    private final MutableLiveData<List<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel>> appointments = new MutableLiveData<>();

    public MutableLiveData<List<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel>> getAppointments() {
        return appointments;
    }

    public void fetchPatientAppointments(Context context){
        ApiService apiService = RetrofitInstance.getApiService(context);

        GetIdFromToken getPatientIdFromToken = new GetIdFromToken();
        int patientId = getPatientIdFromToken.getId(context);

        apiService.getPatientAppointments(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel>> call, Response<List<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel>> response) {
                if(response.isSuccessful()){
                    appointments.postValue(response.body());
                }else{
                    appointments.postValue(new ArrayList<>());
                }
            }
            @Override
            public void onFailure(Call<List<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel>> call, Throwable t) {
                appointments.postValue(new ArrayList<>());
            }
        });
    }
}
