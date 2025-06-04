package com.example.hospital_managment.DoctorDashboard.Chat;

import java.time.LocalDateTime;

public class ChatModel {
    private String id;
    private String userId1;
    private String userId2;
    private String lastMessage;
    private String lastSenderId;
    private String lastUpdated;

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastSenderId() {
        return lastSenderId;
    }

    public void setLastSenderId(String lastSenderId) {
        this.lastSenderId = lastSenderId;
    }

    public String getId() {
        return id;
    }
}
