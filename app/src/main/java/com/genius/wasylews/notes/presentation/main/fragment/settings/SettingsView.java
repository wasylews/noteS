package com.genius.wasylews.notes.presentation.main.fragment.settings;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface SettingsView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showDarkThemeEnabled(boolean darkThemeEnabled);

    void showUseFingerprint(boolean useFingerprintUnlock);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void toggleDarkTheme(boolean use);
}
