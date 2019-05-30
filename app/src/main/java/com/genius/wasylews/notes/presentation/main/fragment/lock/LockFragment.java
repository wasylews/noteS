package com.genius.wasylews.notes.presentation.main.fragment.lock;

import android.os.Bundle;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.presentation.base.BaseFragment;
import com.jakewharton.rxbinding3.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;

public class LockFragment extends BaseFragment implements LockView {

    @Inject
    @InjectPresenter
    LockPresenter presenter;

    @BindView(R.id.unlock) Button unlockButton;

    @ProvidePresenter
    LockPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle args) {
        RxView.clicks(unlockButton).subscribe(unit -> {
            Navigation.findNavController(unlockButton).navigate(R.id.action_unlock);
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_lock;
    }
}
