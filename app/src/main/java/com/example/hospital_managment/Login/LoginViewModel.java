package com.example.hospital_managment.Login;

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

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> loginResult = new MutableLiveData<>();

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    public void loginUser(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8085/")
                .addConverterFactory(ScalarsConverterFactory.create()) // 👈 this goes first!
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        LoginModel loginModel = new LoginModel(email, password);

        apiService.login(loginModel).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        loginResult.postValue("Success: " + response.body().string());
                    } catch (IOException e) {
                        loginResult.postValue("Success, but failed to read response");
                    }
                } else {
                    try {
                        if(response.errorBody().string().contains("404")){
                            loginResult.postValue("Login failed: User not Found");
                        }else{
                            loginResult.postValue("Login failed: Password is Wrong");
                        }
                    } catch (IOException e) {
                        loginResult.postValue("Login failed: unknown error");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                loginResult.postValue("Error: " + t.getMessage());
            }
        });
    }
}