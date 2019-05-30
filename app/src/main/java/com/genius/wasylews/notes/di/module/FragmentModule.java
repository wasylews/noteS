package com.genius.wasylews.notes.di.module;

import com.genius.wasylews.notes.presentation.main.fragment.add.AddNoteFragment;
import com.genius.wasylews.notes.presentation.main.fragment.list.NoteListFragment;
import com.genius.wasylews.notes.presentation.main.fragment.lock.LockFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentModule {

    @ContributesAndroidInjector
    LockFragment lockFragment();

    @ContributesAndroidInjector
    NoteListFragment noteListFragment();

    @ContributesAndroidInjector
    AddNoteFragment addNoteFragment();
}
