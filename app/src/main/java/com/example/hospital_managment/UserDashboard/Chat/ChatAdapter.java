package com.example.hospital_managment.UserDashboard.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital_managment.DoctorDashboard.Chat.Message.MessageView;
import com.example.hospital_managment.GetIdFromToken;
import com.example.hospital_managment.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<com.example.hospital_managment.UserDashboard.Chat.ChatAdapter.ViewHolder> {

    private List<com.example.hospital_managment.UserDashboard.Chat.ChatModel> chatList;
    private final Context context;

    public ChatAdapter(List<com.example.hospital_managment.UserDashboard.Chat.ChatModel>chatList, Context context){
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
    public com.example.hospital_managment.UserDashboard.Chat.ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new com.example.hospital_managment.UserDashboard.Chat.ChatAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull com.example.hospital_managment.UserDashboard.Chat.ChatAdapter.ViewHolder holder, int position) {
        com.example.hospital_managment.UserDashboard.Chat.ChatModel chat = chatList.get(position);

        GetIdFromToken getIdFromToken = new GetIdFromToken();
        String patientId = String.valueOf(getIdFromToken.getId(context));


            holder.chatName.setText(chat.getDoctorName()+" "+chat.getDoctorSurname());

        if (chat.getLastSenderId() == null) {
            holder.chatStatus.setText("Start Chat");
            holder.chatItemLayout.setBackgroundColor(Color.LTGRAY);
            holder.chatStatus.setBackgroundColor(Color.LTGRAY);
        }
        else if(chat.getLastSenderId().equals(patientId)){
            holder.chatStatus.setText("Delivered");
            holder.chatItemLayout.setBackgroundColor(Color.parseColor("#D0F0C0"));
            holder.chatStatus.setBackgroundColor(Color.parseColor("#D0F0C0"));
        }else{
            holder.chatStatus.setText("New Chat");
            holder.chatItemLayout.setBackgroundColor(Color.parseColor("#ADD8E6"));
            holder.chatStatus.setBackgroundColor(Color.parseColor("#ADD8E6"));
        }

        holder.itemView.setOnClickListener(v -> {
            String patientIdd = chat.getUserId1().equals(patientId)
                    ? chat.getUserId2()
                    : chat.getUserId1();
            System.out.println(patientIdd);

            Bundle bundle = new Bundle();
            bundle.putString("chatId",patientIdd);

            MessageView messageFragment = new MessageView();
            messageFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) context;

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, messageFragment)
                    .addToBackStack(null)
                    .commit();
        });

        holder.image.setImageResource(R.drawable.chat_icon);
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