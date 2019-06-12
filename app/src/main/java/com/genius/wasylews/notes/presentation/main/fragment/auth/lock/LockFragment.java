package com.genius.wasylews.notes.presentation.main.fragment.auth.lock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.presentation.base.BaseFragment;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;

public class LockFragment extends BaseFragment implements LockView {

    @Inject
    @InjectPresenter
    LockPresenter presenter;

    @BindView(R.id.edit_password) EditText passwordEdit;
    @BindView(R.id.edit_confirm_password) EditText confirmPasswordEdit;
    @BindView(R.id.check_fingerprint) CheckBox checkFingerprint;
    @BindView(R.id.button_create) Button createButton;

    @ProvidePresenter
    LockPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle args) {
        presenter.addDisposable(RxTextView.textChanges(passwordEdit)
                .map(charSequence -> charSequence.toString().toCharArray())
                .subscribe(text -> {
                    passwordEdit.setError(null);
                    presenter.passwordChanged(text);
                }));

        presenter.addDisposable(RxTextView.textChanges(confirmPasswordEdit)
                .map(charSequence -> charSequence.toString().toCharArray())
                .subscribe(text -> {
                    confirmPasswordEdit.setError(null);
                    presenter.confirmPasswordChanged(text);
                }));

        presenter.addDisposable(RxView.clicks(createButton).subscribe(unit ->
                presenter.createDatabase(requireActivity(), checkFingerprint.isChecked())));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_lock;
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
        passwordEdit.setError(getString(R.string.message_required_field));
    }

    @Override
    public void showConfirmPasswordRequired() {
        confirmPasswordEdit.setError(getString(R.string.message_required_field));
    }

    @Override
    public void showPasswordsDontMatch() {
        confirmPasswordEdit.setError(getString(R.string.message_passwords_dont_match));
    }

    @Override
    public void showFingerprintSupported() {
        checkFingerprint.setVisibility(View.VISIBLE);
    }
}
