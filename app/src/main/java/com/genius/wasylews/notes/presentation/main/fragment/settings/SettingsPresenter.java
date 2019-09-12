package com.genius.wasylews.notes.presentation.main.fragment.settings;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.presentation.base.BasePresenter;
import com.genius.wasylews.notes.presentation.utils.FingerprintHelper;
import com.genius.wasylews.notes.presentation.utils.PrefsHelper;

import javax.inject.Inject;

@InjectViewState
public class SettingsPresenter extends BasePresenter<SettingsView> {

    private PrefsHelper prefsHelper;
    private FingerprintHelper fingerprintHelper;

    @Inject
    public SettingsPresenter(PrefsHelper prefsHelper,
                             FingerprintHelper fingerprintHelper) {
        super();
        this.prefsHelper = prefsHelper;
        this.fingerprintHelper = fingerprintHelper;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showDarkThemeEnabled(prefsHelper.isDarkThemeEnabled());
        if (fingerprintHelper.canAuthenticate()) {
            getViewState().showUseFingerprint(prefsHelper.useFingerprintUnlock());
        }
    }

    public void useDarkThemeChanged(boolean use) {
        prefsHelper.setDarkThemeEnabled(use);
        getViewState().toggleDarkTheme(use);
    }

    public void useFingerprintChanged(boolean use) {
        prefsHelper.setUseFingerprintUnlock(use);
    }
}
