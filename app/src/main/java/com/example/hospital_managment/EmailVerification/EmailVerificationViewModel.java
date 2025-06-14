package com.example.hospital_managment.EmailVerification;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.GetIdFromToken;
import com.example.hospital_managment.PushNotifications.FcmTokenDTO;
import com.example.hospital_managment.Token.RetrofitInstance;
import com.example.hospital_managment.Token.TokenManager;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import okhttp3.ResponseBody;
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

    public void emailVerify(String email, String code, Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8085/")
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
                    TokenManager tokenManager = new TokenManager(context);
                    tokenManager.saveTokens(token,refreshToken);
                    String role = getRoleFromToken(token);
                    tokenManager.saveRole(role);

                    saveFcmToken(context);

                    emailVerifyResult.postValue(role);

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
    public void saveFcmToken(Context context){
        ApiService apiService = RetrofitInstance.getApiService(context);

        GetIdFromToken getIdFromToken=new GetIdFromToken();
        int id = getIdFromToken.getId(context);

        FcmTokenDTO fcmTokenDTO = new FcmTokenDTO();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    System.out.println("IsTask : "+task.isSuccessful());
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        fcmTokenDTO.setFcmToken(token);
                        apiService.addRefreshFcmTokenDto(id,fcmTokenDTO).enqueue(new Callback<>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                System.out.println("Response:"+response.isSuccessful());
                                if(response.isSuccessful()){
                                    System.out.println("Success");
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                            }
                        });
                    }
                });
    }

    public String getRoleFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) return null;

            String payload = new String(Base64.decode(parts[1], Base64.URL_SAFE));
            JSONObject jsonObject = new JSONObject(payload);

            return jsonObject.getString("role");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

