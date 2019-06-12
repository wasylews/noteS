package com.genius.wasylews.notes.presentation.main.fragment.auth.unlock;

import com.arellomobile.mvp.MvpView;

public interface UnlockView extends MvpView {

    void showMessage(String message);

    void showNoteList();

    void showAuthFailed();

    void showPasswordRequired();

    void showCreateLock();

    void showFingerprintUnlock();

    void showEnableFingerprint();
}
