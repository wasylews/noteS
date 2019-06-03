package com.genius.wasylews.notes.domain.usecase.auth;

import android.text.TextUtils;
import android.util.Base64;

import androidx.fragment.app.FragmentActivity;

import com.genius.wasylews.notes.domain.base.completable.CompletableAsyncUseCase;
import com.genius.wasylews.notes.domain.utils.StringArrayUtils;
import com.genius.wasylews.notes.presentation.utils.AuthHelper;
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationFail;

import javax.crypto.Cipher;
import javax.inject.Inject;

import io.reactivex.Completable;

public class FingerprintUnlockUseCase extends CompletableAsyncUseCase {

    private FingerprintAuthUseCase fingerprintAuthUseCase;
    private AuthHelper authHelper;
    private PasswordUnlockUseCase passwordUnlockUseCase;
    private FragmentActivity activity;

    @Inject
    public FingerprintUnlockUseCase(FingerprintAuthUseCase fingerprintAuthUseCase,
                                    AuthHelper authHelper,
                                    PasswordUnlockUseCase passwordUnlockUseCase) {
        this.fingerprintAuthUseCase = fingerprintAuthUseCase;
        this.authHelper = authHelper;
        this.passwordUnlockUseCase = passwordUnlockUseCase;
    }

    public FingerprintUnlockUseCase with(FragmentActivity activity) {
        this.activity = activity;
        return this;
    }

    @Override
    protected Completable buildTask() {
        String encryptedCredits = new String(Base64.decode(authHelper.getEncryptedPassword(), Base64.DEFAULT));
        if (!TextUtils.isEmpty(encryptedCredits)) {
            String[] parts = encryptedCredits.split(FingerprintLockUseCase.SEPARATOR);
            byte[] iv = Base64.decode(parts[1], Base64.DEFAULT);
            return fingerprintAuthUseCase.with(activity, Cipher.DECRYPT_MODE, iv).buildTask()
                    .flatMapCompletable(cipher -> {

                        byte[] encryptedPassword = Base64.decode(parts[0], Base64.DEFAULT);
                        byte[] password = cipher.doFinal(encryptedPassword);

                        // Using password unlock use case for hash validation
                        return passwordUnlockUseCase.with(StringArrayUtils.toString(password).toCharArray())
                                .buildTask();

                    });
        }
        return Completable.error(new AuthenticationFail());
    }
}
