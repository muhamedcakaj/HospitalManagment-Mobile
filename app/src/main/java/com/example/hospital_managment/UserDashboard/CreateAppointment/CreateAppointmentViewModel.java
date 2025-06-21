package com.example.hospital_managment.UserDashboard.CreateAppointment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.DoctorDashboard.Appointments.AppointmentsModel;
import com.example.hospital_managment.GetIdFromToken;
import com.example.hospital_managment.Token.RetrofitInstance;
import com.example.hospital_managment.UserDashboard.Chat.GetDoctorsDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAppointmentViewModel extends ViewModel {

    private final MutableLiveData<List<GetDoctorsDTO>>doctors=new MutableLiveData<>();
    private final MutableLiveData<List<AppointmentsModel>>appointments=new MutableLiveData<>();
    public MutableLiveData<List<GetDoctorsDTO>>getDoctorsList(){
        return doctors;
    }

    public MutableLiveData<List<AppointmentsModel>>getAppointments(){
        return appointments;
    }
    public void fetchDoctors(Context context){
        ApiService apiService = RetrofitInstance.getApiService(context);

        apiService.getAllDoctors().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<GetDoctorsDTO>> call, @NonNull Response<List<GetDoctorsDTO>> response) {
                if(response.isSuccessful()){
                    doctors.postValue(response.body());
                }else{
                    doctors.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetDoctorsDTO>> call, @NonNull Throwable t) {
                doctors.postValue(new ArrayList<>());
            }
        });
    }

    public void fetchDoctorAppointments(Context context,int doctorId){
        ApiService apiService = RetrofitInstance.getApiService(context);

        apiService.getAppointments(doctorId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<AppointmentsModel>> call, @NonNull Response<List<AppointmentsModel>> response) {
                if(response.isSuccessful()){
                    appointments.postValue(response.body());
                }else{
                    appointments.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AppointmentsModel>> call, @NonNull Throwable t) {
                appointments.postValue(new ArrayList<>());
            }
        });
    }

    public void bookAnAppointment(Context context,int doctorId,String time,String date){
        GetIdFromToken getIdFromToken = new GetIdFromToken();

        AppointmentCreateDTO appointmentCreateDTO = new AppointmentCreateDTO();
        appointmentCreateDTO.setUserId(getIdFromToken.getId(context));
        appointmentCreateDTO.setDoctorId(doctorId);

        appointmentCreateDTO.setLocalDate(date);
        appointmentCreateDTO.setLocalTime(time + ":00");

        ApiService apiService=RetrofitInstance.getApiService(context);

        apiService.bookAnAppointment(appointmentCreateDTO).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    System.out.println("Successfully added appointment");
                }else{
                    System.out.println("Appointment not added");
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                System.out.println("Appointment Failed to add");
            }
        });
    }
}
