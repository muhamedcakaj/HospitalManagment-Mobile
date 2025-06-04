package com.example.hospital_managment.DoctorDashboard.Diagnoses;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.Token.RetrofitInstance;
import com.example.hospital_managment.Token.TokenManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosesViewModel extends ViewModel {

    private final MutableLiveData<List<DiagnosesModel>> diagnoses = new MutableLiveData<>();

    public LiveData<List<DiagnosesModel>> getDiagnoses() {
        return diagnoses;
    }

    public void fetchDiagnoses(Context context) {
        System.out.println("VIEWMODEL");
        ApiService apiService = RetrofitInstance.getApiService(context);

        TokenManager tokenManager = new TokenManager(context);
        int doctorId = Integer.parseInt(Objects.requireNonNull(getDoctorIdFromToken(tokenManager.getAccessToken())));
        apiService.getDoctorDiagnoses(doctorId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<DiagnosesModel>> call, @NonNull Response<List<DiagnosesModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    diagnoses.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DiagnosesModel>> call, @NonNull Throwable t) {
                diagnoses.postValue(new ArrayList<>());
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