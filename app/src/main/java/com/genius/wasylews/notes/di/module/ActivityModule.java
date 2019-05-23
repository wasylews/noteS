package com.genius.wasylews.notes.di.module;

import com.genius.wasylews.notes.presentation.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector
    MainActivity mainActivity();
}
