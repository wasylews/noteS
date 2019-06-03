package com.genius.wasylews.notes.di.module;

import com.genius.wasylews.notes.presentation.main.fragment.add.AddNoteFragment;
import com.genius.wasylews.notes.presentation.main.fragment.auth.lock.LockFragment;
import com.genius.wasylews.notes.presentation.main.fragment.auth.unlock.UnlockFragment;
import com.genius.wasylews.notes.presentation.main.fragment.list.NoteListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentModule {

    @ContributesAndroidInjector
    LockFragment lockFragment();

    @ContributesAndroidInjector
    UnlockFragment unlockFragment();

    @ContributesAndroidInjector
    NoteListFragment noteListFragment();

    @ContributesAndroidInjector
    AddNoteFragment addNoteFragment();
}
