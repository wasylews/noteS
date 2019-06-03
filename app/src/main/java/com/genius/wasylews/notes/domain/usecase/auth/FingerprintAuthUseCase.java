package com.genius.wasylews.notes.domain.usecase.auth;

import android.os.Build;
import android.os.Handler;

import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.domain.base.single.SingleAsyncUseCase;
import com.github.pwittchen.rxbiometric.library.RxBiometric;
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationFail;
import com.github.pwittchen.rxbiometric.library.throwable.BiometricNotSupported;
import com.github.pwittchen.rxbiometric.library.validation.RxPreconditions;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class FingerprintAuthUseCase extends SingleAsyncUseCase<Cipher> {

    private FragmentActivity activity;
    private int mode;
    private byte[] iv;
    private BiometricPrompt.CryptoObject cryptoObject;
    private Key authKey;

    @Inject
    public FingerprintAuthUseCase(BiometricPrompt.CryptoObject cryptoObject,
                                  Key authKey) {
        this.cryptoObject = cryptoObject;
        this.authKey = authKey;
    }

    public FingerprintAuthUseCase with(FragmentActivity activity, int mode) {
       return with(activity, mode, null);
    }

    public FingerprintAuthUseCase with(FragmentActivity activity, int mode, byte[] iv) {
        this.activity = activity;
        this.mode = mode;
        this.iv = iv;
        return this;
    }

    private Executor getMainExecutor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return activity.getMainExecutor();
        }

        return new Executor() {
            private final Handler handler = new Handler(activity.getMainLooper());
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    @Override
    protected Single<Cipher> buildTask() {
        try {
            if (cryptoObject.getCipher() != null) {
                if (mode == Cipher.ENCRYPT_MODE) {
                    cryptoObject.getCipher().init(mode, authKey);
                } else {
                    cryptoObject.getCipher().init(mode, authKey, new IvParameterSpec(iv));
                }
            } else {
                return Single.error(new AuthenticationFail());
            }
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            return Single.error(e);
        }

        return RxPreconditions.hasBiometricSupport(activity)
                .flatMapCompletable(hasSupport -> {
                    if (!hasSupport) {
                        return Completable.error(new BiometricNotSupported());
                    }

                    return RxBiometric.title(activity.getString(R.string.title_fingerprint))
                            .description(activity.getString(R.string.message_fingerprint_description))
                            .executor(getMainExecutor())
                            .negativeButtonText(activity.getString(android.R.string.cancel))
                            .negativeButtonListener((dialog, which) -> {
                            })
                            .build()
                            .authenticate(activity, cryptoObject);
                }).toSingleDefault(cryptoObject.getCipher());
    }
}
