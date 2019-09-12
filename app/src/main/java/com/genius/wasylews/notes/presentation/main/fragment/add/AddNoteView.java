package com.genius.wasylews.notes.presentation.main.fragment.add;

import com.arellomobile.mvp.MvpView;
import com.genius.wasylews.notes.data.db.model.NoteModel;

public interface AddNoteView extends MvpView {

    void goBack();

    void showMessage(String message);

    void showNote(NoteModel note);
}
