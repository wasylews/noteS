package com.genius.wasylews.notes.di.module;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;

import com.genius.wasylews.notes.BuildConfig;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FingerprintAuthModule {

    private static final String ANDROID_KEYSTORE_NAME = "AndroidKeyStore";
    private static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    private static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    private static final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    private static final String TRANSFORMATION = KEY_ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING;
    private static final String KEY_NAME = BuildConfig.APPLICATION_ID + ".fingerprint_key";

    @Provides
    @Singleton
    KeyStore provideKeyStore() {
        KeyStore store = null;
        try {
            store = KeyStore.getInstance(ANDROID_KEYSTORE_NAME);
            store.load(null);
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return store;
    }

    @Provides
    @Singleton
    KeyGenParameterSpec provideKeyGenSpec() {
        return new KeyGenParameterSpec.Builder(KEY_NAME,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(BLOCK_MODE)
                .setEncryptionPaddings(ENCRYPTION_PADDING)
                .setUserAuthenticationRequired(true)
                .build();
    }

    @Provides
    @Singleton
    KeyGenerator provideKeyGenerator(KeyGenParameterSpec keyGenSpec) {
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance(KEY_ALGORITHM, ANDROID_KEYSTORE_NAME);
            generator.init(keyGenSpec);
        } catch (NoSuchAlgorithmException | NoSuchProviderException |
                InvalidAlgorithmParameterException | IllegalStateException e) {
            e.printStackTrace();
        }
        return generator;
    }

    @Provides
    @Singleton
    @Nullable
    Key provideKey(KeyStore keyStore, KeyGenerator keyGenerator) {
        Key key = null;
        try {
            if (!keyStore.isKeyEntry(KEY_NAME)) {
                // TODO: method returns same key as keyStore.getKey?
                keyGenerator.generateKey();
            }
            key = keyStore.getKey(KEY_NAME, null);
        } catch (KeyStoreException | NoSuchAlgorithmException |
                 IllegalStateException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return key;
    }

    @Provides
    @Singleton
    Cipher provideCipher() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return cipher;
    }

    @Provides
    @Singleton
    BiometricPrompt.CryptoObject provideCryptoObject(Cipher cipher) {
        return new BiometricPrompt.CryptoObject(cipher);
    }
}
