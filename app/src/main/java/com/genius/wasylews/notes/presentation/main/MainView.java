package com.genius.wasylews.notes.presentation.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface MainView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void toggleDarkMode(boolean darkThemeEnabled);
}
