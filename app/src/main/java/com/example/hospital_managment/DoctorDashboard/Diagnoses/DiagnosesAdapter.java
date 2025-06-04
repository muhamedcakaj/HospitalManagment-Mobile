package com.example.hospital_managment.DoctorDashboard.Diagnoses;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital_managment.R;
import java.util.List;

public class DiagnosesAdapter extends RecyclerView.Adapter<DiagnosesAdapter.ViewHolder> {

    private List<DiagnosesModel> diagnosesList;

    public DiagnosesAdapter(List<DiagnosesModel> diagnosesList) {
        this.diagnosesList = diagnosesList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, time, description;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewPatientName);
            date = itemView.findViewById(R.id.textViewDate);
            time = itemView.findViewById(R.id.textViewTime);
            description = itemView.findViewById(R.id.textViewDescription);
            icon = itemView.findViewById(R.id.imageViewIcon);
        }
    }

    @NonNull
    @Override
    public DiagnosesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diagnosis, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DiagnosesModel diagnosis = diagnosesList.get(position);
        holder.name.setText("Doctor ID: " + diagnosis.getUserId());
        holder.description.setText(diagnosis.getDiagnosis());


        String dt = diagnosis.getDiagnosis_date();
        String[]split=dt.split("T");
        holder.date.setText(split[0]);
        String[]split2=split[1].split(":");
        holder.time.setText(split2[0]+":"+split2[1]);


        holder.icon.setImageResource(R.drawable.diagnose_list);
    }

    @Override
    public int getItemCount() {
        return diagnosesList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<DiagnosesModel> newList) {
        this.diagnosesList = newList;
        notifyDataSetChanged();
    }
}