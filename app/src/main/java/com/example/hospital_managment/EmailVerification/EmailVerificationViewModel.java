package com.example.hospital_managment.EmailVerification;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class EmailVerificationViewModel extends ViewModel {

    private final MutableLiveData<String>emailVerifyResult = new MutableLiveData<>();

    public LiveData<String> getLoginResult(){
        return emailVerifyResult;
    }

    public void emailVerify(String email,String code){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102:8085/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        EmailVerificationModel emailVerificationModel = new EmailVerificationModel(email,code);

        apiService.verifyEmail(emailVerificationModel).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponseDTO> call, @NonNull Response<AuthResponseDTO> response) {
                if (response.isSuccessful() && response.body()!=null) {

                    AuthResponseDTO authResponse = response.body();

                    String token = authResponse.getToken();
                    String refreshToken = authResponse.getRefreshToken();

                    emailVerifyResult.postValue("Success" + token);

                } else {
                    emailVerifyResult.postValue("Verification failed: Code is wrong or has expired");
                }
            }
            @Override
            public void onFailure(@NonNull Call<AuthResponseDTO> call, @NonNull Throwable t) {
                emailVerifyResult.postValue("Error: " + t.getMessage());
            }
        });
    }
}
