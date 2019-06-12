package com.genius.wasylews.notes.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefsHelper {

    private static final String PREFS_NAME = "shared_prefs";
    private static final String KEY_ENCRYPTED_PASSWORD = "password";
    private static final String KEY_PASSWORD_HASH = "password_hash";
    private static final String KEY_DARK_THEME = "dark_theme";
    private static final String KEY_USE_FINGERPRINT = "use_fingerprint";

    private SharedPreferences prefs;

    @Inject
    public PrefsHelper(Context context) {
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

    public boolean isDarkThemeEnabled() {
        return prefs.getBoolean(KEY_DARK_THEME, false);
    }

    public void setDarkThemeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_DARK_THEME, enabled).apply();
    }

    public boolean useFingerprintUnlock() {
        return prefs.getBoolean(KEY_USE_FINGERPRINT, false);
    }

    public void setUseFingerprintUnlock(boolean use) {
        prefs.edit().putBoolean(KEY_USE_FINGERPRINT, use).apply();
    }
}
