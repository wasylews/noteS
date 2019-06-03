package com.genius.wasylews.notes.presentation.main.fragment.auth.lock;

import com.arellomobile.mvp.MvpView;

public interface LockView extends MvpView {

    void showMessage(String message);

    void showNoteList();

    void showAuthFailed();

    void showPasswordRequired();

    void showConfirmPasswordRequired();

    void showPasswordsDontMatch();
}
