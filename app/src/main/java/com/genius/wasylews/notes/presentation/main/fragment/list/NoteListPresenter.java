package com.genius.wasylews.notes.presentation.main.fragment.list;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.presentation.base.BasePresenter;

import javax.inject.Inject;

@InjectViewState
public class NoteListPresenter extends BasePresenter<NoteListView> {

    @Inject
    public NoteListPresenter() {
        super();
    }
}
