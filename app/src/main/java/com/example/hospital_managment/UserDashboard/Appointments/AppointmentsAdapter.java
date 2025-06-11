package com.example.hospital_managment.UserDashboard.Appointments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital_managment.R;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsAdapter.ViewHolder> {

    private List<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel> appointmentsList;
    private final Context context;

    private com.example.hospital_managment.UserDashboard.Appointments.AppointmentsViewModel appointmentsViewModel;
    public AppointmentsAdapter(List<com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel> appointmentsList, Context context, AppointmentsViewModel appointmentsViewModel) {
        this.appointmentsList = appointmentsList;
        this.context=context;
        this.appointmentsViewModel = appointmentsViewModel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfAppointments, dateOfAppointments;
        Button appointmentStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            nameOfAppointments = itemView.findViewById(R.id.nameOfAppointments);
            dateOfAppointments = itemView.findViewById(R.id.dateOfAppointments);
            appointmentStatus = itemView.findViewById(R.id.appointmentStatus);
        }
    }

    @NonNull
    @Override
    public com.example.hospital_managment.UserDashboard.Appointments.AppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointments2, parent, false);
        return new com.example.hospital_managment.UserDashboard.Appointments.AppointmentsAdapter.ViewHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull com.example.hospital_managment.UserDashboard.Appointments.AppointmentsAdapter.ViewHolder holder, int position) {
        com.example.hospital_managment.UserDashboard.Appointments.AppointmentsModel appointments = appointmentsList.get(position);

        holder.nameOfAppointments.setText("Doctor ID: " +appointments.getDoctorId());
        holder.dateOfAppointments.setText(appointments.getLocalDate()+","+appointments.getLocalTime());

        if(appointments.getAppointemntStatus().equals("Approved")){
            holder.appointmentStatus.setText("Approved");
            holder.appointmentStatus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#88E6A0")));
        }else if(appointments.getAppointemntStatus().equals("Canceled")){
            holder.appointmentStatus.setText("Canceled");
            holder.appointmentStatus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EF3227")));
        }else{
            holder.appointmentStatus.setText("Pending");
            holder.appointmentStatus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFC26D")));
        }
    }

    @Override
    public int getItemCount() {
        return this.appointmentsList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<AppointmentsModel> newList) {
        this.appointmentsList = newList;
        notifyDataSetChanged();
    }
}