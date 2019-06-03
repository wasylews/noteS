package com.genius.wasylews.notes.presentation.main.fragment.auth.unlock;

import androidx.fragment.app.FragmentActivity;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.domain.usecase.auth.FingerprintUnlockUseCase;
import com.genius.wasylews.notes.domain.usecase.auth.PasswordUnlockUseCase;
import com.genius.wasylews.notes.domain.utils.StringArrayUtils;
import com.genius.wasylews.notes.presentation.base.BasePresenter;
import com.genius.wasylews.notes.presentation.utils.AuthHelper;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

@InjectViewState
public class UnlockPresenter extends BasePresenter<UnlockView> {

    private FingerprintUnlockUseCase fingerprintUnlockUseCase;
    private PasswordUnlockUseCase passwordUnlockUseCase;
    private AuthHelper authHelper;

    @Inject
    public UnlockPresenter(FingerprintUnlockUseCase fingerprintUnlockUseCase,
                           PasswordUnlockUseCase passwordUnlockUseCase,
                           AuthHelper authHelper) {
        super();
        this.fingerprintUnlockUseCase = fingerprintUnlockUseCase;
        this.passwordUnlockUseCase = passwordUnlockUseCase;
        this.authHelper = authHelper;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (!authHelper.lockExists()) {
            getViewState().showCreateLock();
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
                getViewState().showMessage(e.getMessage());
            }
        }));
    }
}
