package com.genius.wasylews.notes.domain.usecase.auth;

import android.util.Base64;

import androidx.fragment.app.FragmentActivity;

import com.genius.wasylews.notes.domain.base.completable.CompletableAsyncUseCase;
import com.genius.wasylews.notes.domain.utils.StringArrayUtils;
import com.genius.wasylews.notes.presentation.utils.AuthHelper;

import javax.crypto.Cipher;
import javax.inject.Inject;

import io.reactivex.Completable;

public class FingerprintLockUseCase extends CompletableAsyncUseCase {

    public static final String SEPARATOR = ":";
    private FingerprintAuthUseCase fingerprintAuthUseCase;
    private AuthHelper authHelper;
    private PasswordLockUseCase passwordLockUseCase;
    private FragmentActivity activity;
    private char[] password;

    @Inject
    public FingerprintLockUseCase(FingerprintAuthUseCase fingerprintAuthUseCase,
                                  AuthHelper authHelper,
                                  PasswordLockUseCase passwordLockUseCase) {
        this.fingerprintAuthUseCase = fingerprintAuthUseCase;
        this.authHelper = authHelper;
        this.passwordLockUseCase = passwordLockUseCase;
    }

    public FingerprintLockUseCase with(FragmentActivity activity, char[] password) {
        this.activity = activity;
        this.password = password;
        return this;
    }

    @Override
    protected Completable buildTask() {
        return fingerprintAuthUseCase.with(activity, Cipher.ENCRYPT_MODE).buildTask()
                .flatMapCompletable(cipher -> {
                    byte[] passwordBytes = StringArrayUtils.toString(password).getBytes();

                    String base64Password = Base64.encodeToString(cipher.doFinal(passwordBytes), Base64.DEFAULT);
                    String base64Iv = Base64.encodeToString(cipher.getIV(), Base64.DEFAULT);

                    //noinspection StringBufferReplaceableByString
                    byte[] encryptedCredits = new StringBuilder()
                            .append(base64Password)
                            .append(SEPARATOR)
                            .append(base64Iv)
                            .toString()
                            .getBytes();
                    String base64Credits = Base64.encodeToString(encryptedCredits, Base64.DEFAULT);
                    authHelper.setEncryptedPassword(base64Credits);

                    // Using password lock use case for storing hash
                   return passwordLockUseCase.with(password).buildTask();
                });
    }
}
