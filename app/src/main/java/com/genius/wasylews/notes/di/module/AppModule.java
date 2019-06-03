package com.genius.wasylews.notes.di.module;

import android.content.Context;

import androidx.annotation.Nullable;

import com.genius.wasylews.notes.App;
import com.genius.wasylews.notes.presentation.utils.AuthHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        ActivityModule.class,
        FragmentModule.class,
        FingerprintAuthModule.class
})
public abstract class AppModule {

    @Binds
    abstract Context provideContext(App context);

    @Provides
    @Singleton
    static AuthHelper provideAuthHelper(Context context) {
        return new AuthHelper(context);
    }

    @Provides
    @Nullable
    static MessageDigest provideSha256() {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.reset();
            return instance;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
