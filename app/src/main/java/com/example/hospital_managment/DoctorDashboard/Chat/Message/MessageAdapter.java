package com.example.hospital_managment.DoctorDashboard.Chat.Message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital_managment.DoctorDashboard.GetDoctorIdFromToken;
import com.example.hospital_managment.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageModel> messageList;
    private final Context context;

    public MessageAdapter(List<MessageModel>messageList,Context context){
        this.messageList=messageList;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeHolderMessage;
        TextView dateOfMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolderMessage = itemView.findViewById(R.id.placeHolderMessage);
            dateOfMessage = itemView.findViewById(R.id.dateOfMessage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel message = this.messageList.get(position);

        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        String doctorId = String.valueOf(getDoctorIdFromToken.getDoctorId(context));

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.placeHolderMessage.getLayoutParams();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) holder.dateOfMessage.getLayoutParams();


        String dt = message.getTimestamp();
        String[]split=dt.split("T");
        String[]split2=split[1].split(":");
        String fullDateTime=split[0]+"/"+split2[0]+":"+split2[1];

        holder.dateOfMessage.setText(fullDateTime);
        holder.placeHolderMessage.setText(message.getContent());

        if(message.getSenderId().trim().equals(doctorId)){
            holder.placeHolderMessage.setBackgroundColor(Color.parseColor("#D0F0C0"));
            holder.dateOfMessage.setBackgroundColor(Color.parseColor("#D0F0C0"));

            params.startToStart = ConstraintLayout.LayoutParams.UNSET;
            params2.startToStart=ConstraintLayout.LayoutParams.UNSET;

           params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
           params2.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;

           holder.placeHolderMessage.setGravity(Gravity.END);
           holder.dateOfMessage.setGravity(Gravity.END);
        }else{
            holder.placeHolderMessage.setBackgroundColor(Color.parseColor("#E0E0E0"));
            holder.dateOfMessage.setBackgroundColor(Color.parseColor("#E0E0E0"));

            params.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            params2.endToEnd = ConstraintLayout.LayoutParams.UNSET;

            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params2.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;

            holder.placeHolderMessage.setGravity(Gravity.START);
            holder.dateOfMessage.setGravity(Gravity.START);
        }
        holder.placeHolderMessage.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return this.messageList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<MessageModel> newList) {
        this.messageList = newList;
        notifyDataSetChanged();
    }
}
