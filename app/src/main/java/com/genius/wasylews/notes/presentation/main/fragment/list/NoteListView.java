package com.genius.wasylews.notes.presentation.main.fragment.list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.genius.wasylews.notes.data.db.model.NoteModel;

import java.util.List;

public interface NoteListView extends MvpView {

    void showNotes(List<NoteModel> noteModels);

    void showMessage(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showNoteRemoved(NoteModel note, int position);

    void showNoteInserted(NoteModel note, int position);
}
