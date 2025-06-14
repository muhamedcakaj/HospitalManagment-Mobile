package com.example.hospital_managment.Token;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class TokenManager {
    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_ACCESS = "access_token";
    private static final String KEY_REFRESH = "refresh_token";

    private static final String KEY_ROLE = "role";

    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveTokens(String accessToken, String refreshToken) {
        prefs.edit()
                .putString(KEY_ACCESS, accessToken)
                .putString(KEY_REFRESH, refreshToken)
                .apply();
    }
    public void saveRole(String role){
        prefs.edit()
                .putString(KEY_ROLE,role)
                .apply();

    }

    public String getAccessToken() {
        return prefs.getString(KEY_ACCESS, null);
    }

    public String getRefreshToken() {
        return prefs.getString(KEY_REFRESH, null);
    }
    public String getRole(){
        return prefs.getString(KEY_ROLE,null);
    }
    public boolean isTokenExpired() {
        try {
            String[] parts = getRefreshToken().split("\\.");
            if (parts.length != 3) return true;

            byte[] decodedBytes = android.util.Base64.decode(parts[1], android.util.Base64.DEFAULT);
            String payload = new String(decodedBytes, java.nio.charset.StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(payload);
            long exp = json.getLong("exp");
            long now = System.currentTimeMillis() / 1000;

            return now >= exp;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
    public void clearTokens() {
        prefs.edit().clear().apply();
    }
}