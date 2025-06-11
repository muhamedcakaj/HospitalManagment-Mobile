package com.example.hospital_managment.DoctorDashboard.Chat;

import android.content.Context;

import androidx.annotation.NonNull;
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

    private final MutableLiveData<List<ChatModel>> chats = new MutableLiveData<>();

    public MutableLiveData<List<ChatModel>>getChats(){
        return chats;
    }

    public void fetchChats(Context context) {

        ApiService apiservice = RetrofitInstance.getApiService(context);

        GetIdFromToken getDoctorIdFromToken = new GetIdFromToken();

        int userId = getDoctorIdFromToken.getId(context);

        apiservice.getChats(userId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<ChatModel>> call, @NonNull Response<List<ChatModel>> response) {
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
}

