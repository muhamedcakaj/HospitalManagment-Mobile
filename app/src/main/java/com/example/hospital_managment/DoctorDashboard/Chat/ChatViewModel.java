package com.example.hospital_managment.DoctorDashboard.Chat;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.Token.RetrofitInstance;
import com.example.hospital_managment.Token.TokenManager;

import org.json.JSONObject;

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

        TokenManager tokenManager = new TokenManager(context);

        int userId = Integer.parseInt(getDoctorIdFromToken(tokenManager.getAccessToken()));

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

    public static String getDoctorIdFromToken(String token) {
        try {
            String[] parts = token.split("\\."); // Header, Payload, Signature
            if (parts.length != 3) {
                return null;
            }

            String payload = parts[1];
            byte[] decodedBytes = Base64.decode(payload, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            String decodedPayload = new String(decodedBytes, "UTF-8");

            JSONObject jsonObject = new JSONObject(decodedPayload);
            return jsonObject.getString("sub"); // 'sub' is usually the user ID in JWT

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

