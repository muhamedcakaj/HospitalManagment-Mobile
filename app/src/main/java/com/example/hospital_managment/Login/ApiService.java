package com.example.hospital_managment.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/login")
    Call<ResponseBody> login(@Body LoginModel loginModel);
}