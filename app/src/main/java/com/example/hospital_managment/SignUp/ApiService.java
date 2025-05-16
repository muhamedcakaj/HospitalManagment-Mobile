package com.example.hospital_managment.SignUp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/signup")
    Call<ResponseBody> signUp(@Body SignUpModel signUpModel);
}
