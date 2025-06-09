package com.example.hospital_managment.DoctorDashboard.CreateDiagnoses;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.DoctorDashboard.GetDoctorIdFromToken;
import com.example.hospital_managment.Token.RetrofitInstance;
import com.example.hospital_managment.Token.TokenManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateDiagnosesViewModel extends ViewModel {
    private final MutableLiveData<String> createDiagnosesResult = new MutableLiveData<>();

    public LiveData<String> getDiagnosesResult() {
        return createDiagnosesResult;
    }

    public void createDiagnosis(Context context, String email, String diagnosis) {
        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        int doctorId = getDoctorIdFromToken.getDoctorId(context);

        CreateDiagnosesModel model = new CreateDiagnosesModel(doctorId, email, diagnosis);
        ApiService apiService = RetrofitInstance.getApiService(context);

        apiService.createDiagnoses(model).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    createDiagnosesResult.postValue("Diagnose has been submitted successfully");
                }else{
                    createDiagnosesResult.postValue("Enter a valid email address");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                createDiagnosesResult.postValue("The system is down try again later");
            }
        });
    }
}
