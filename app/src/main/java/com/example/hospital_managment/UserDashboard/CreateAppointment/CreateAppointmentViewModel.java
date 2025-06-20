package com.example.hospital_managment.UserDashboard.CreateAppointment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.Token.RetrofitInstance;
import com.example.hospital_managment.UserDashboard.Chat.GetDoctorsDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAppointmentViewModel extends ViewModel {

    private final MutableLiveData<List<GetDoctorsDTO>>doctors=new MutableLiveData<>();
    public MutableLiveData<List<GetDoctorsDTO>>getDoctorsList(){
        return doctors;
    }

    public void fetchDoctors(Context context){
        ApiService apiService = RetrofitInstance.getApiService(context);

        apiService.getAllDoctors().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<GetDoctorsDTO>> call, @NonNull Response<List<GetDoctorsDTO>> response) {
                if(response.isSuccessful()){
                    doctors.postValue(response.body());
                }else{
                    doctors.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetDoctorsDTO>> call, @NonNull Throwable t) {
                doctors.postValue(new ArrayList<>());
            }
        });
    }
}
