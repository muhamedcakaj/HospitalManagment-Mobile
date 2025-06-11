package com.example.hospital_managment.UserDashboard.Diagnoses;

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

public class DiagnosesViewModel extends ViewModel {
    private final MutableLiveData<List<com.example.hospital_managment.UserDashboard.Diagnoses.DiagnosesModel>> diagnoses = new MutableLiveData<>();

    public LiveData<List<com.example.hospital_managment.UserDashboard.Diagnoses.DiagnosesModel>> getDiagnoses() {
        return diagnoses;
    }

    public void fetchDiagnoses(Context context) {
        ApiService apiService = RetrofitInstance.getApiService(context);
        GetIdFromToken getPatientIdFromToken = new GetIdFromToken();
        int patientId = getPatientIdFromToken.getId(context);
        apiService.getPatientDiagnoses(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<com.example.hospital_managment.UserDashboard.Diagnoses.DiagnosesModel>> call, @NonNull Response<List<com.example.hospital_managment.UserDashboard.Diagnoses.DiagnosesModel>> response) {
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
}
