package com.example.hospital_managment;

import com.example.hospital_managment.DoctorDashboard.Appointments.AppointmentUpdateDTO;
import com.example.hospital_managment.DoctorDashboard.Appointments.AppointmentsModel;
import com.example.hospital_managment.DoctorDashboard.Chat.ChatModel;
import com.example.hospital_managment.DoctorDashboard.Chat.Message.MessageModel;
import com.example.hospital_managment.DoctorDashboard.CreateDiagnoses.CreateDiagnosesModel;
import com.example.hospital_managment.DoctorDashboard.Diagnoses.DiagnosesModel;
import com.example.hospital_managment.DoctorDashboard.Profile.ProfileModel;
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
import retrofit2.http.PUT;
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

    @GET("appointments/doctor/{id}")
    Call<List<AppointmentsModel>>getAppointments(@Path("id")int id);

    @PUT("appointments/doctors/{id}/status")
    Call<ResponseBody>updateAppointmentsStatus(@Path("id") int id, @Body AppointmentUpdateDTO appointmentUpdateDTO);
    @GET("doctors/{id}")
    Call<ProfileModel> getDoctorsPersonalInfo(@Path("id")int id);

    @PUT("doctors/{id}")
    Call<ResponseBody>updateDoctorPersonalInfo(@Path("id") int id,@Body ProfileModel profileModel);

    //Patient API-S below

    @GET("appointments/user/{id}")
    Call<List<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel>>getPatientAppointments(@Path("id")int id);

    @GET("diagnosis/user/{id}")
    Call<List<com.example.hospital_managment.UserDashboard.Diagnoses.DiagnosesModel>>getPatientDiagnoses(@Path("id")int id);


}
