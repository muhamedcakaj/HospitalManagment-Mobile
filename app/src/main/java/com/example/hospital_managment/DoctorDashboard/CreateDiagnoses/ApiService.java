package com.example.hospital_managment.DoctorDashboard.CreateDiagnoses;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("diagnosis/doctor")
    Call<ResponseBody>createDiagnoses(@Body CreateDiagnosesModel createDiagnosesModel);

}
