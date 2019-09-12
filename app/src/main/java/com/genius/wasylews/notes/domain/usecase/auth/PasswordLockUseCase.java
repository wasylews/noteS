package com.genius.wasylews.notes.domain.usecase.auth;

import android.util.Base64;

import androidx.annotation.Nullable;

import com.genius.wasylews.notes.domain.base.completable.CompletableAsyncUseCase;
import com.genius.wasylews.notes.domain.repository.NoteRepository;
import com.genius.wasylews.notes.domain.utils.StringArrayUtils;
import com.genius.wasylews.notes.presentation.utils.PrefsHelper;

import java.security.MessageDigest;

import javax.inject.Inject;

import io.reactivex.Completable;

public class PasswordLockUseCase extends CompletableAsyncUseCase {

    private MessageDigest sha256;
    private PrefsHelper prefsHelper;
    private NoteRepository repository;
    private char[] password;

    @Inject
    public PasswordLockUseCase(@Nullable MessageDigest sha256,
                               PrefsHelper prefsHelper,
                               NoteRepository repository) {
        this.sha256 = sha256;
        this.prefsHelper = prefsHelper;
        this.repository = repository;
    }

    public PasswordLockUseCase with(char[] password) {
        this.password = password;
        return this;
    }

    @Override
    protected Completable buildTask() {
        return Completable.fromCallable(() -> {
            byte[] hash = sha256.digest(StringArrayUtils.toString(password).getBytes());
            String base64Hash = Base64.encodeToString(hash, Base64.DEFAULT);
            prefsHelper.setPasswordHash(base64Hash);
            return repository.openDatabase(password);
        });
    }
}
