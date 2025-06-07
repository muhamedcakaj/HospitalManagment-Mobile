package com.example.hospital_managment.DoctorDashboard.Chat.Message;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospital_managment.ApiService;
import com.example.hospital_managment.DoctorDashboard.GetDoctorIdFromToken;
import com.example.hospital_managment.Token.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.time.LocalDateTime;

public class MessageViewModel extends ViewModel {

    private final MutableLiveData<List<MessageModel>> message = new MutableLiveData<>();

    public MutableLiveData<List<MessageModel>>getMessage(){
        return message;
    }
    private final OkHttpClient client = new OkHttpClient();
    private WebSocket webSocket;
    private boolean isConnected = false;
    public void fetchMessage(Context context,String patientId){
        System.out.println(patientId);
        ApiService apiService= RetrofitInstance.getApiService(context);

        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        String doctorId = String.valueOf(getDoctorIdFromToken.getDoctorId(context));

        apiService.getMessage(doctorId,patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<MessageModel>> call,@NonNull Response<List<MessageModel>> response) {
                if(response.isSuccessful()){
                    message.postValue(response.body());
                }else{
                    message.postValue(new ArrayList<>());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<MessageModel>> call, @NonNull Throwable t) {
                message.postValue(new ArrayList<>());
            }
        });
    }
    /// /////////////////////////////////////////////////////////////////
    public void sendMessage(String receiverId, String content, Context context) {
        GetDoctorIdFromToken getDoctorIdFromToken = new GetDoctorIdFromToken();
        String senderId = String.valueOf(getDoctorIdFromToken.getDoctorId(context));
        try {
            JSONObject messageJson = new JSONObject();
            messageJson.put("senderId", senderId);
            messageJson.put("receiverId", receiverId);
            messageJson.put("content", content);

            List<MessageModel> currentMessages = message.getValue();
            if (currentMessages == null) {
                currentMessages = new ArrayList<>();
            }

            MessageModel newMessage = new MessageModel();
            newMessage.setSenderId(senderId);
            newMessage.setReceiverId(receiverId);
            newMessage.setContent(content);
            newMessage.setTimestamp(String.valueOf(LocalDateTime.now()));

            currentMessages.add(newMessage);
            message.postValue(currentMessages);

            if (webSocket != null) {
                webSocket.send(messageJson.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void connectWebSocket(Context context) {
        if (isConnected) return;

        String userId = String.valueOf(new GetDoctorIdFromToken().getDoctorId(context));
        Request request = new Request.Builder()
                .url("ws://10.0.2.2:8087/ws/chat?userId=" + userId)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull okhttp3.Response response) {
                isConnected = true;
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                try {
                    JSONObject json = new JSONObject(text);
                    MessageModel incomingMessage = new MessageModel();
                    incomingMessage.setSenderId(json.getString("senderId"));
                    incomingMessage.setReceiverId(json.getString("receiverId"));
                    incomingMessage.setContent(json.getString("content"));
                    incomingMessage.setTimestamp(LocalDateTime.now().toString());

                    List<MessageModel> currentMessages = message.getValue();
                    if (currentMessages == null) currentMessages = new ArrayList<>();
                    currentMessages.add(incomingMessage);
                    message.postValue(currentMessages);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, okhttp3.Response response) {
                Log.e("WebSocket", "Failed: " + t.getMessage());
                isConnected = false;
            }

            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                isConnected = false;
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (webSocket != null) {
            webSocket.cancel();
        }
        isConnected = false;
        client.dispatcher().executorService().shutdown();
    }
}
