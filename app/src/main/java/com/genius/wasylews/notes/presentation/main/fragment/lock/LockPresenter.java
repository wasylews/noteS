package com.genius.wasylews.notes.presentation.main.fragment.lock;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.domain.repository.NoteRepository;
import com.genius.wasylews.notes.presentation.base.BasePresenter;

import javax.inject.Inject;

@InjectViewState
public class LockPresenter extends BasePresenter<LockView> {

    @Inject
    public LockPresenter() {
        super();
    }
}
