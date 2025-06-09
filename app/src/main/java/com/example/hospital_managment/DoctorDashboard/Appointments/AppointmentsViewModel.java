package com.example.hospital_managment.DoctorDashboard.Appointments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.DoctorDashboard.GetDoctorIdFromToken;
import com.example.hospital_managment.Token.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsViewModel extends ViewModel {
    private final MutableLiveData<List<AppointmentsModel>> appointments = new MutableLiveData<>();

    public MutableLiveData<List<AppointmentsModel>> getAppointments() {
        return appointments;
    }

    public void fetchDoctorAppointments(Context context){
        ApiService apiService = RetrofitInstance.getApiService(context);

        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        int doctorId = getDoctorIdFromToken.getDoctorId(context);

        apiService.getAppointments(doctorId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<AppointmentsModel>> call, @NonNull Response<List<AppointmentsModel>> response) {
                if(response.isSuccessful()){
                    appointments.postValue(response.body());
                }else{
                    appointments.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AppointmentsModel>> call, @NonNull Throwable t) {
                appointments.postValue(new ArrayList<>());
            }
        });
    }

    public void updateAppointmentsStatus(Context context,int id,AppointmentUpdateDTO appointmentUpdateDTO){
        ApiService apiService=RetrofitInstance.getApiService(context);

        apiService.updateAppointmentsStatus(id,appointmentUpdateDTO).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    fetchDoctorAppointments(context);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
