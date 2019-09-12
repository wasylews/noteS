package com.genius.wasylews.notes.presentation.main.fragment.auth.unlock;

import androidx.fragment.app.FragmentActivity;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.domain.usecase.auth.FingerprintLockUseCase;
import com.genius.wasylews.notes.domain.usecase.auth.FingerprintUnlockUseCase;
import com.genius.wasylews.notes.domain.usecase.auth.PasswordUnlockUseCase;
import com.genius.wasylews.notes.domain.utils.StringArrayUtils;
import com.genius.wasylews.notes.presentation.base.BasePresenter;
import com.genius.wasylews.notes.presentation.utils.FingerprintHelper;
import com.genius.wasylews.notes.presentation.utils.PrefsHelper;
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationFail;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

@InjectViewState
public class UnlockPresenter extends BasePresenter<UnlockView> {

    private FingerprintUnlockUseCase fingerprintUnlockUseCase;
    private FingerprintLockUseCase fingerprintLockUseCase;
    private PasswordUnlockUseCase passwordUnlockUseCase;
    private PrefsHelper prefsHelper;
    private FingerprintHelper fingerprintHelper;
    private char[] password;

    @Inject
    public UnlockPresenter(FingerprintUnlockUseCase fingerprintUnlockUseCase,
                           FingerprintLockUseCase fingerprintLockUseCase,
                           PasswordUnlockUseCase passwordUnlockUseCase,
                           PrefsHelper prefsHelper,
                           FingerprintHelper fingerprintHelper) {
        super();
        this.fingerprintUnlockUseCase = fingerprintUnlockUseCase;
        this.fingerprintLockUseCase = fingerprintLockUseCase;
        this.passwordUnlockUseCase = passwordUnlockUseCase;
        this.prefsHelper = prefsHelper;
        this.fingerprintHelper = fingerprintHelper;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (!prefsHelper.lockExists()) {
            getViewState().showCreateLock();
        }

        if (prefsHelper.useFingerprintUnlock() &&
                prefsHelper.getEncryptedPassword() != null &&
                fingerprintHelper.canAuthenticate()) {
            getViewState().showFingerprintUnlock();
        }
    }

    public void unlockDatabase() {
        if (!prefsHelper.useFingerprintUnlock() && fingerprintHelper.canAuthenticate()) {
            getViewState().showEnableFingerprint();
        } else {
            passwordUnlock(password);
        }
    }

    public void passwordUnlock(char[] password) {
        if (StringArrayUtils.isEmpty(password)) {
            getViewState().showPasswordRequired();
        } else {
            addDisposable(passwordUnlockUseCase.with(password).execute(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    getViewState().showNoteList();
                }

                @Override
                public void onError(Throwable e) {
                    getViewState().showMessage(e.getMessage());
                }
            }));
        }
    }

    public void fingerprintUnlock(FragmentActivity activity) {
        addDisposable(fingerprintUnlockUseCase.with(activity).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getViewState().showNoteList();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof AuthenticationFail) {
                    getViewState().showAuthFailed();
                } else {
                    getViewState().showMessage(e.getMessage());
                }
            }
        }));
    }

    public void createFingerprintLock(FragmentActivity activity, char[] password) {
        addDisposable(fingerprintLockUseCase.with(activity, password).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                prefsHelper.setUseFingerprintUnlock(true);
                getViewState().showNoteList();
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showMessage(e.getMessage());
            }
        }));
    }

    public void passwordChanged(char[] password) {
        this.password = password;
    }
}
