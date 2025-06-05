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

    public MessageAdapter(List<MessageModel>messageModel,Context context){
        this.messageList=messageList;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeHolderMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolderMessage = itemView.findViewById(R.id.placeHolderMessage);
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

        if(message.getSenderId().equals(doctorId)||message.getReceiverId().equals(doctorId)){
            holder.placeHolderMessage.setBackgroundColor(Color.parseColor("#D0F0C0"));
            params.startToStart = ConstraintLayout.LayoutParams.UNSET;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.placeHolderMessage.setGravity(Gravity.END);
        }
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
