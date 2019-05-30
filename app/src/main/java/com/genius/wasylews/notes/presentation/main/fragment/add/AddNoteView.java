package com.genius.wasylews.notes.presentation.main.fragment.add;

import com.arellomobile.mvp.MvpView;

public interface AddNoteView extends MvpView {

    void goBack();

    void showMessage(String message);
}
