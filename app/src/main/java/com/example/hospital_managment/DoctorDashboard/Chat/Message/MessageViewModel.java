package com.example.hospital_managment.DoctorDashboard.Chat.Message;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.DoctorDashboard.GetDoctorIdFromToken;
import com.example.hospital_managment.Token.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {

    private final MutableLiveData<List<MessageModel>> message = new MutableLiveData<>();

    public MutableLiveData<List<MessageModel>>getMessage(){
        return message;
    }

    public void fetchMessage(Context context,String patientId){
        System.out.println(patientId);
        ApiService apiService= RetrofitInstance.getApiService(context);

        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        String doctorId = String.valueOf(getDoctorIdFromToken.getDoctorId(context));

        apiService.getMessage(doctorId,patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<MessageModel>> call,@NonNull Response<List<MessageModel>> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body().get(1).getContent());
                    message.postValue(response.body());
                }else{
                    message.postValue(new ArrayList<>());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<MessageModel>> call, @NonNull Throwable t) {
                message.postValue(new ArrayList<>());
            }
        });
    }
}
