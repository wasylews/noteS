package com.genius.wasylews.notes.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthHelper {

    private static final String PREFS_NAME = "shared_prefs";
    private static final String KEY_ENCRYPTED_PASSWORD = "password";
    private static final String KEY_PASSWORD_HASH = "password_hash";

    private SharedPreferences prefs;

    @Inject
    public AuthHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setEncryptedPassword(@NonNull String password) {
        prefs.edit().putString(KEY_ENCRYPTED_PASSWORD, password).apply();
    }

    @Nullable
    public String getEncryptedPassword() {
        return prefs.getString(KEY_ENCRYPTED_PASSWORD, null);
    }

    public void setPasswordHash(String hash) {
        prefs.edit().putString(KEY_PASSWORD_HASH, hash).apply();
    }

    @Nullable
    public String getPasswordHash() {
        return prefs.getString(KEY_PASSWORD_HASH, null);
    }

    public boolean lockExists() {
        return getPasswordHash() != null;
    }
}
