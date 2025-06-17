package com.example.hospital_managment.DoctorDashboard.Appointments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hospital_managment.R;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    private List<AppointmentsModel> appointmentsList;
    private final Context context;

    private AppointmentsViewModel appointmentsViewModel;
    public AppointmentsAdapter(List<AppointmentsModel> appointmentsList, Context context, AppointmentsViewModel appointmentsViewModel) {
        this.appointmentsList = appointmentsList;
        this.context=context;
        this.appointmentsViewModel = appointmentsViewModel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfAppointments, dateOfAppointments;
        Button changeAppointmentStatus, appointmentStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            nameOfAppointments = itemView.findViewById(R.id.nameOfAppointments);
            dateOfAppointments = itemView.findViewById(R.id.dateOfAppointments);
            changeAppointmentStatus = itemView.findViewById(R.id.changeAppointmentStatus);
            appointmentStatus = itemView.findViewById(R.id.appointmentStatus);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointments, parent, false);
        return new AppointmentsAdapter.ViewHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentsModel appointments = appointmentsList.get(position);

        holder.nameOfAppointments.setText("Patient : " +appointments.getUserName()+" "+appointments.getUserSurname());
        holder.dateOfAppointments.setText(appointments.getLocalDate()+","+appointments.getLocalTime());
        holder.changeAppointmentStatus.setText(appointments.getAppointemntStatus());

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

        holder.changeAppointmentStatus.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.changeAppointmentStatus);
            popup.getMenuInflater().inflate(R.menu.satus_menu, popup.getMenu());
            int appointmentId=appointments.getId();
            popup.setOnMenuItemClickListener(item -> {
                String selectedStatus = item.getTitle().toString();
                AppointmentUpdateDTO appointmentUpdateDTO = new AppointmentUpdateDTO();
                appointmentUpdateDTO.setStatus(selectedStatus);

                appointmentsViewModel.updateAppointmentsStatus(context,appointmentId,appointmentUpdateDTO);
                return true;
            });

            popup.show();
        });
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