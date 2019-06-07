package com.qbent.enfinsapp.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AuthHelper {
    private static final String JWT_KEY_USERNAME = "username";
    private static final String PREFS = "prefs";
    private static final String PREF_TOKEN = "pref_token";
    private SharedPreferences mPrefs;

    private static AuthHelper sInstance;


    private AuthHelper(@NonNull Context context) {
        mPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sInstance = this;
    }

    public static AuthHelper getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new AuthHelper(context);
        }
        return sInstance;
    }

    public void setIdToken(@NonNull String token) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_TOKEN, token);
        editor.apply();
    }

    @Nullable
    public String getIdToken(){
        return mPrefs.getString(PREF_TOKEN, null);
    }

    public boolean isLoggedIn() {
        String token = getIdToken();
        return token != null;
    }
}
