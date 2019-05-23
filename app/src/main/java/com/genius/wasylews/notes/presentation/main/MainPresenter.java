package com.genius.wasylews.notes.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    public MainPresenter() {
        super();
    }
}
