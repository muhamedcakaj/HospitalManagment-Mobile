package com.example.hospital_managment.DoctorDashboard.Profile;

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

    private final MutableLiveData<ProfileModel>profileData = new MutableLiveData<>();

    public MutableLiveData<ProfileModel>getProfileData() {
        return profileData;
    }

    public void fetchPersonalInfoFromDoctor(Context context){
        GetIdFromToken getDoctorIdFromToken = new GetIdFromToken();
        int doctorId = getDoctorIdFromToken.getId(context);

        ApiService apiService = RetrofitInstance.getApiService(context);

        apiService.getDoctorsPersonalInfo(doctorId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                if(response.isSuccessful()){
                    profileData.postValue(response.body());
                }else{
                    profileData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                profileData.postValue(null);
            }
        });
    }
    public void updateDoctorPersonalInfo(String firstName,String lastName,String specialization,String description,Context context){
        GetIdFromToken getDoctorIdFromToken = new GetIdFromToken();
        int doctorId = getDoctorIdFromToken.getId(context);

        ApiService apiService=RetrofitInstance.getApiService(context);

        ProfileModel profileModel = new ProfileModel();
        profileModel.setFirst_name(firstName);
        profileModel.setLast_name(lastName);
        profileModel.setSpecialization(specialization);
        profileModel.setDescription(description);

        apiService.updateDoctorPersonalInfo(doctorId,profileModel).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    fetchPersonalInfoFromDoctor(context);
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
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    TokenManager tokenManager = new TokenManager(context);
                    tokenManager.clearTokens();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Failed");
            }
        });
    }
}
