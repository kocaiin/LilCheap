package com.homework.shopapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefManager {
    private static final String TAG = "SharedPrefManager";
    private static final String SHARED_PREF_NAME = "shoppe_prefs";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PASSWORD = "user_password";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private static SharedPrefManager mInstance;
    private final SharedPreferences prefs;

    private SharedPrefManager(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context.getApplicationContext());
        }
        return mInstance;
    }

    public void register(String email, String password) {
        Log.d(TAG, "Registering user: " + email);
        prefs.edit()
                .putString(KEY_EMAIL, email)
                .putString(KEY_PASSWORD, password)
                .apply();
    }

    public boolean login(String email, String password) {
        String savedEmail = prefs.getString(KEY_EMAIL, null);
        String savedPassword = prefs.getString(KEY_PASSWORD, null);

        Log.d(TAG, "Login attempt - Input: " + email + ", Saved: " + savedEmail);

        boolean success = email.equals(savedEmail) && password.equals(savedPassword);
        if (success) {
            prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply();
        }

        Log.d(TAG, "Login " + (success ? "successful" : "failed"));
        return success;
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        Log.d(TAG, "Logging out user");
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply();
    }

    public String getUserEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }
}
