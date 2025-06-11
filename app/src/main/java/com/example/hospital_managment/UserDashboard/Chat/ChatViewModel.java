package com.example.hospital_managment.UserDashboard.Chat;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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

public class ChatViewModel extends ViewModel {

    private final MutableLiveData<List<com.example.hospital_managment.UserDashboard.Chat.ChatModel>> chats = new MutableLiveData<>();
    private final MutableLiveData<List<GetDoctorsDTO>> allDoctors = new MutableLiveData<>();

    public MutableLiveData<List<com.example.hospital_managment.UserDashboard.Chat.ChatModel>>getChats(){
        return chats;
    }

    public LiveData<List<GetDoctorsDTO>> getAllDoctors() {
        return allDoctors;
    }

    public void fetchChats(Context context) {

        ApiService apiservice = RetrofitInstance.getApiService(context);

        GetIdFromToken getPatientIdFromToken = new GetIdFromToken();

        int patientId = getPatientIdFromToken.getId(context);

        apiservice.getChatsForPatients(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<com.example.hospital_managment.UserDashboard.Chat.ChatModel>> call, Response<List<com.example.hospital_managment.UserDashboard.Chat.ChatModel>> response) {
                if(response.isSuccessful()){
                    chats.postValue(response.body());
                }else{
                    chats.postValue(new ArrayList<>());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ChatModel>> call, @NonNull Throwable t) {
                chats.postValue(new ArrayList<>());

            }
        });
    }

    public void fetchAllDoctors(Context context) {
        ApiService apiService = RetrofitInstance.getApiService(context);
        apiService.getAllDoctors().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<GetDoctorsDTO>> call, Response<List<GetDoctorsDTO>> response) {
                if (response.isSuccessful()) {
                    allDoctors.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GetDoctorsDTO>> call, Throwable t) {
                allDoctors.postValue(new ArrayList<>());
            }
        });
    }
}
