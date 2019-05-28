package com.genius.wasylews.notes.presentation.main;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.lifecycle.LifecycleObserver;
import androidx.navigation.Navigation;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.presentation.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView, LifecycleObserver {

    @InjectPresenter
    @Inject
    MainPresenter presenter;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onUserLeaveHint() {
        Navigation.findNavController(this, R.id.nav_host).navigate(R.id.action_lock);
        super.onUserLeaveHint();
    }
}
