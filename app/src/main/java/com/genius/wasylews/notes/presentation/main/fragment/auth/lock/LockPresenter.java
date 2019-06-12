package com.genius.wasylews.notes.presentation.main.fragment.auth.lock;

import androidx.fragment.app.FragmentActivity;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.domain.usecase.auth.FingerprintLockUseCase;
import com.genius.wasylews.notes.domain.usecase.auth.PasswordLockUseCase;
import com.genius.wasylews.notes.domain.utils.StringArrayUtils;
import com.genius.wasylews.notes.presentation.base.BasePresenter;
import com.genius.wasylews.notes.presentation.utils.FingerprintHelper;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

@InjectViewState
public class LockPresenter extends BasePresenter<LockView> {

    private char[] password;
    private char[] confirmPassword;
    private FingerprintLockUseCase fingerprintLockUseCase;
    private PasswordLockUseCase passwordLockUseCase;
    private FingerprintHelper fingerprintHelper;

    @Inject
    public LockPresenter(FingerprintLockUseCase fingerprintLockUseCase,
                         PasswordLockUseCase passwordLockUseCase,
                         FingerprintHelper fingerprintHelper) {
        super();
        this.fingerprintLockUseCase = fingerprintLockUseCase;
        this.passwordLockUseCase = passwordLockUseCase;
        this.fingerprintHelper = fingerprintHelper;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (fingerprintHelper.canAuthenticate()) {
            getViewState().showFingerprintSupported();
        }
    }

    public void passwordChanged(char[] password) {
        this.password = password;
    }

    public void confirmPasswordChanged(char[] confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void createDatabase(FragmentActivity activity, boolean fingerprintAuth) {
        if (!validatePassword()) return;

        if (fingerprintAuth) {
            fingerprintLock(activity);
        } else {
            passwordLock();
        }
    }

    private void passwordLock() {
        addDisposable(passwordLockUseCase.with(password).execute(new DisposableCompletableObserver() {
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

    private void fingerprintLock(FragmentActivity activity) {
        addDisposable(fingerprintLockUseCase.with(activity, password).execute(new DisposableCompletableObserver() {
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

    private boolean validatePassword() {
        if (StringArrayUtils.isEmpty(password)) {
            getViewState().showPasswordRequired();
            return false;
        }

        if (StringArrayUtils.isEmpty(confirmPassword)) {
            getViewState().showConfirmPasswordRequired();
            return false;
        }

        if (!StringArrayUtils.equals(password, confirmPassword)) {
            getViewState().showPasswordsDontMatch();
            return false;
        }
        return true;
    }
}
