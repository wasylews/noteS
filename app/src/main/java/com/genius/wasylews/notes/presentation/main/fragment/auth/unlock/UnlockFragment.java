package com.genius.wasylews.notes.presentation.main.fragment.auth.unlock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.presentation.base.BaseFragment;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;

public class UnlockFragment extends BaseFragment implements UnlockView {

    @Inject
    @InjectPresenter
    UnlockPresenter presenter;

    @BindView(R.id.edit_password) EditText passwordEdit;
    @BindView(R.id.button_unlock) Button unlockButton;
    @BindView(R.id.text_fingerprint) TextView fingerprintAuthButton;

    @ProvidePresenter
    UnlockPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle args) {
        presenter.addDisposable(RxView.clicks(unlockButton).subscribe(unit ->
                presenter.passwordUnlock(passwordEdit.getText().toString().toCharArray())));

        presenter.addDisposable(RxView.clicks(fingerprintAuthButton).subscribe(unit ->
                presenter.fingerprintUnlock(requireActivity())));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_unlock;
    }

    @Override
    public void showMessage(String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showNoteList() {
        navigate(R.id.action_get_notes);
    }

    @Override
    public void showAuthFailed() {
        showMessage(getString(R.string.message_auth_failed));
    }

    @Override
    public void showPasswordRequired() {
        showMessage(getString(R.string.message_required_field));
    }

    @Override
    public void showCreateLock() {
        navigate(R.id.action_create_lock);
    }
}
