package com.example.hospital_managment;

import com.example.hospital_managment.DoctorDashboard.Chat.ChatModel;
import com.example.hospital_managment.DoctorDashboard.Chat.Message.MessageModel;
import com.example.hospital_managment.DoctorDashboard.CreateDiagnoses.CreateDiagnosesModel;
import com.example.hospital_managment.DoctorDashboard.Diagnoses.DiagnosesModel;
import com.example.hospital_managment.EmailVerification.AuthResponseDTO;
import com.example.hospital_managment.EmailVerification.EmailVerificationModel;
import com.example.hospital_managment.Login.LoginModel;
import com.example.hospital_managment.SignUp.SignUpModel;
import com.example.hospital_managment.Token.RefreshToken.RefreshRequest;
import com.example.hospital_managment.Token.RefreshToken.TokenResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/signup")
    Call<ResponseBody> signUp(@Body SignUpModel signUpModel);

    @POST("auth/login")
    Call<ResponseBody> login(@Body LoginModel loginModel);

    @POST("auth/confirmEmail")
    Call<AuthResponseDTO> verifyEmail(@Body EmailVerificationModel verifyEmailModel);

    @POST("auth/refresh-token")
    Call<TokenResponse> refreshToken(@Body RefreshRequest request);

    @POST("diagnosis/doctor")
    Call<ResponseBody>createDiagnoses(@Body CreateDiagnosesModel createDiagnosesModel);

    @GET("diagnosis/doctor/{id}")
    Call<List<DiagnosesModel>> getDoctorDiagnoses(@Path("id") int doctorId);

    @GET("chat/conversations/{userId}")
    Call<List<ChatModel>> getChats(@Path("userId") int userId);

    @GET("chat/messages/{user1}/{user2}")
    Call<List<MessageModel>> getMessage(@Path("user1")String user1,@Path("user2")String user2);

}
