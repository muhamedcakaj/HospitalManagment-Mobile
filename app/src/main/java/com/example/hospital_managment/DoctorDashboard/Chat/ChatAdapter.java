package com.example.hospital_managment.DoctorDashboard.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hospital_managment.DoctorDashboard.GetDoctorIdFromToken;
import com.example.hospital_managment.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatModel> chatList;
    private final Context context;

    public ChatAdapter(List<ChatModel>chatList, Context context){
        this.chatList=chatList;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatName;
        Button chatStatus;

        ImageView image;

        View chatItemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            chatName = itemView.findViewById(R.id.chatName);
            chatStatus = itemView.findViewById(R.id.chatStatus);
            image=itemView.findViewById(R.id.image);
            chatItemLayout=itemView.findViewById(R.id.chatItemLayout);
        }
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ChatModel chat = chatList.get(position);

        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        String doctorId = String.valueOf(getDoctorIdFromToken.getDoctorId(context));

        if(chat.getUserId1().equals(doctorId)){
            holder.chatName.setText("Patient ID: " + chat.getUserId2());
        }else{
            holder.chatName.setText("Patient ID: " + chat.getUserId1());
        }

        if(chat.getLastSenderId().equals(doctorId)){
            holder.chatStatus.setText("Delivered");
            holder.chatItemLayout.setBackgroundColor(Color.parseColor("#D0F0C0"));
            holder.chatStatus.setBackgroundColor(Color.parseColor("#D0F0C0"));
        }else{
            holder.chatStatus.setText("New Chat");
            holder.chatItemLayout.setBackgroundColor(Color.parseColor("#ADD8E6"));
            holder.chatStatus.setBackgroundColor(Color.parseColor("#ADD8E6"));
        }
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("chatId",chat.getId()); // or any other data you want to pass

            MessageFragment messageFragment = new MessageFragment();
            messageFragment.setArguments(bundle);

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, messageFragment)
                    .addToBackStack(null)
                    .commit();
        });
        holder.image.setImageResource(R.drawable.message_icon);
    }

    @Override
    public int getItemCount() {
        return this.chatList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<ChatModel> newList) {
        this.chatList = newList;
        notifyDataSetChanged();
    }
}
