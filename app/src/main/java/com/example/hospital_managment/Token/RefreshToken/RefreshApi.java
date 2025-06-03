package com.example.hospital_managment.Token.RefreshToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RefreshApi {
    @POST("auth/refresh-token")
    Call<TokenResponse> refreshToken(@Body RefreshRequest request);
}
