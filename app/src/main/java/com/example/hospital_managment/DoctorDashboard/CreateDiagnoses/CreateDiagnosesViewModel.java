package com.example.hospital_managment.DoctorDashboard.CreateDiagnoses;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        TokenManager tokenManager = new TokenManager(context);
        int doctorId = Integer.parseInt(getDoctorIdFromToken(tokenManager.getAccessToken()));

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
