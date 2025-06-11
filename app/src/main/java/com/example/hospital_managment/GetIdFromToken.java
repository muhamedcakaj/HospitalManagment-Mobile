package com.example.hospital_managment;

import android.content.Context;
import android.util.Base64;

import com.example.hospital_managment.Token.TokenManager;

import org.json.JSONObject;

public class GetIdFromToken {

    public int getId(Context context){
        try {
            TokenManager tokenManager = new TokenManager(context);
            String token =tokenManager.getAccessToken();

            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return -1;
            }

            String payload = parts[1];
            byte[] decodedBytes = Base64.decode(payload, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            String decodedPayload = new String(decodedBytes, "UTF-8");

            JSONObject jsonObject = new JSONObject(decodedPayload);
            return Integer.parseInt(jsonObject.getString("sub"));

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
