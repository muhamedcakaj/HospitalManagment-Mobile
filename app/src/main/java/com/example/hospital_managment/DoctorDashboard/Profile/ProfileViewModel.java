package com.example.hospital_managment.DoctorDashboard.Profile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.DoctorDashboard.GetDoctorIdFromToken;
import com.example.hospital_managment.Token.RetrofitInstance;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ProfileModel>profileData = new MutableLiveData<>();

    public MutableLiveData<ProfileModel>getProfileData() {
        return profileData;
    }

    public void fetchPersonalInfoFromDoctor(Context context){
        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        int doctorId = getDoctorIdFromToken.getDoctorId(context);

        ApiService apiService = RetrofitInstance.getApiService(context);

        apiService.getDoctorsPersonalInfo(doctorId).enqueue(new Callback<ProfileModel>() {
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
        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        int doctorId = getDoctorIdFromToken.getDoctorId(context);

        ApiService apiService=RetrofitInstance.getApiService(context);

        ProfileModel profileModel = new ProfileModel();
        profileModel.setFirst_name(firstName);
        profileModel.setLast_name(lastName);
        profileModel.setSpecialization(specialization);
        profileModel.setDescription(description);

        apiService.updateDoctorPersonalInfo(doctorId,profileModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    fetchPersonalInfoFromDoctor(context);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
