package com.genius.wasylews.notes.presentation.main.fragment.list;

import com.arellomobile.mvp.MvpView;
import com.genius.wasylews.notes.data.db.model.NoteModel;

import java.util.List;

public interface NoteListView extends MvpView {

    void showNotes(List<NoteModel> noteModels);

    void showMessage(String message);
}
