package com.example.hospital_managment.EmailVerification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/confirmEmail")
    Call<AuthResponseDTO> verifyEmail(@Body EmailVerificationModel verifyEmailModel);
}
