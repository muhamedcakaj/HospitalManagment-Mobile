package com.example.hospital_managment.Token;

import com.example.hospital_managment.Token.RefreshToken.RefreshApi;
import com.example.hospital_managment.Token.RefreshToken.RefreshRequest;
import com.example.hospital_managment.Token.RefreshToken.TokenResponse;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAuthenticator implements Authenticator {
    private final TokenManager tokenManager;

    public TokenAuthenticator(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (responseCount(response) >= 2) return null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8085/")  // your backend
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RefreshApi refreshApi = retrofit.create(RefreshApi.class);
        RefreshRequest refreshRequest = new RefreshRequest(tokenManager.getRefreshToken());

        retrofit2.Response<TokenResponse> refreshResponse = refreshApi.refreshToken(refreshRequest).execute();

        if (refreshResponse.isSuccessful() && refreshResponse.body() != null) {

            TokenResponse newTokens = refreshResponse.body();
            System.out.println("Token: " + newTokens.getToken());
            System.out.println("RefreshToken: "+newTokens.getRefreshToken());

            tokenManager.saveTokens(newTokens.getToken(), newTokens.getRefreshToken());

            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newTokens.getToken())
                    .build();
        }

        tokenManager.clearTokens(); // optional
        return null;
    }

    private int responseCount(Response response) {
        int count = 1;
        while ((response = response.priorResponse()) != null) count++;
        return count;
    }
}