package com.example.hospital_managment.UserDashboard.Profile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.GetIdFromToken;
import com.example.hospital_managment.Token.RetrofitInstance;
import com.example.hospital_managment.Token.TokenManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<com.example.hospital_managment.UserDashboard.Profile.ProfileModel> profileData = new MutableLiveData<>();

    public MutableLiveData<com.example.hospital_managment.UserDashboard.Profile.ProfileModel>getProfileDataPatient() {
        return profileData;
    }

    public void fetchPersonalInfoFromPatient(Context context){
        GetIdFromToken getIdFromToken = new GetIdFromToken();
        int patientId = getIdFromToken.getId(context);

        ApiService apiService = RetrofitInstance.getApiService(context);

        apiService.getPersonalInfoOfPatient(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<com.example.hospital_managment.UserDashboard.Profile.ProfileModel> call, @NonNull Response<com.example.hospital_managment.UserDashboard.Profile.ProfileModel> response) {
                if(response.isSuccessful()){
                    profileData.postValue(response.body());
                }else{
                    profileData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.example.hospital_managment.UserDashboard.Profile.ProfileModel> call, @NonNull Throwable t) {
                profileData.postValue(null);
            }
        });
    }
    public void updateDoctorPersonalInfo(String firstName,String lastName,Context context){
        GetIdFromToken getPatientIdFromToken = new GetIdFromToken();
        int patientId = getPatientIdFromToken.getId(context);

        ApiService apiService=RetrofitInstance.getApiService(context);

        com.example.hospital_managment.UserDashboard.Profile.ProfileModel profileModel = new ProfileModel();
        profileModel.setFirst_name(firstName);
        profileModel.setSecond_name(lastName);

        apiService.updatePersonalInfoOfPatient(patientId,profileModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    fetchPersonalInfoFromPatient(context);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }
    public void deleteFcmTokenFromUser(Context context){
        GetIdFromToken getIdFromToken = new GetIdFromToken();
        int id = getIdFromToken.getId(context);
        ApiService apiService = RetrofitInstance.getApiService(context);

        apiService.deleteFcmTokenFromUser(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    TokenManager tokenManager = new TokenManager(context);
                    tokenManager.clearTokens();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }
}
