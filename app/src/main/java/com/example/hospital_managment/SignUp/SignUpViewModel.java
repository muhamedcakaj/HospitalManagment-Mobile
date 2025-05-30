package com.example.hospital_managment.SignUp;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SignUpViewModel extends ViewModel {

    private final MutableLiveData<String> signUpResult = new MutableLiveData<>();

    public LiveData<String> getSingUpResult() {
        return signUpResult;
    }

    public void singUpUser(String firstName, String lastName, String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8085/")
                .addConverterFactory(ScalarsConverterFactory.create()) // 👈 this goes first!
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        com.example.hospital_managment.SignUp.ApiService apiService = retrofit.create(ApiService.class);
        SignUpModel signUpModel = new SignUpModel(firstName, lastName, email, password);

        apiService.signUp(signUpModel).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        signUpResult.postValue("Success: " + response.body().string());
                    } catch (IOException e) {
                        signUpResult.postValue("Success, but failed to read response");
                    }
                } else {
                    try {
                        if (response.errorBody().string().contains("400")) {
                            signUpResult.postValue("SignUp failed:The email exists in database");
                        }
                    } catch (IOException e) {
                        signUpResult.postValue("Login failed: unknown error");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                signUpResult.postValue("Error: " + t.getMessage());
            }


        });

    }
}
