package com.genius.wasylews.notes.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.presentation.base.BasePresenter;
import com.genius.wasylews.notes.presentation.utils.PrefsHelper;

import javax.inject.Inject;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private PrefsHelper prefsHelper;

    @Inject
    public MainPresenter(PrefsHelper prefsHelper) {
        super();
        this.prefsHelper = prefsHelper;
    }

    public void loadSettings() {
        getViewState().toggleDarkMode(prefsHelper.isDarkThemeEnabled());
    }
}
