package com.genius.wasylews.notes.presentation.base;

import android.os.Bundle;

import com.arellomobile.mvp.MvpDelegate;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.LayoutRes;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Base activity with AndroidX support
 */
public abstract class BaseActivity extends DaggerAppCompatActivity {
    private MvpDelegate<? extends BaseActivity> mMvpDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        onViewReady(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    protected abstract void onViewReady(Bundle savedInstanceState);

    protected abstract @LayoutRes int getLayoutResourceId();

    @Override
    protected void onStart() {
        super.onStart();

        getMvpDelegate().onAttach();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMvpDelegate().onAttach();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    protected void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getMvpDelegate().onDestroyView();

        if (isFinishing()) {
            getMvpDelegate().onDestroy();
        }
    }

    /**
     * @return The {@link MvpDelegate} being used by this Activity.
     */
    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }
        return mMvpDelegate;
    }
}