package com.genius.wasylews.notes.domain.usecase.auth;

import android.util.Base64;

import androidx.annotation.Nullable;

import com.genius.wasylews.notes.domain.base.completable.CompletableAsyncUseCase;
import com.genius.wasylews.notes.domain.repository.NoteRepository;
import com.genius.wasylews.notes.domain.utils.StringArrayUtils;
import com.genius.wasylews.notes.presentation.utils.AuthHelper;

import java.security.MessageDigest;

import javax.inject.Inject;

import io.reactivex.Completable;

public class PasswordUnlockUseCase extends CompletableAsyncUseCase {

    private MessageDigest sha256;
    private AuthHelper authHelper;
    private NoteRepository repository;
    private char[] password;

    @Inject
    public PasswordUnlockUseCase(@Nullable MessageDigest sha256,
                                 AuthHelper authHelper,
                                 NoteRepository repository) {
        this.sha256 = sha256;
        this.authHelper = authHelper;
        this.repository = repository;
    }

    public PasswordUnlockUseCase with(char[] password) {
        this.password = password;
        return this;
    }

    @Override
    protected Completable buildTask() {
        return Completable.fromCallable(() -> {
            byte[] passwordHash = Base64.decode(authHelper.getPasswordHash(), Base64.DEFAULT);
            byte[] currentPasswordHash = sha256.digest(StringArrayUtils.toString(password).getBytes());

            if (StringArrayUtils.equals(currentPasswordHash, passwordHash)) {
                return repository.openDatabase(password);
            } else {
                return Completable.error(new Exception("Password hash doesn't match"));
            }
        });
    }
}
