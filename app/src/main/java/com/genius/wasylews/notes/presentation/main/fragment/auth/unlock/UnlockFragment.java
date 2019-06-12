package com.genius.wasylews.notes.presentation.main.fragment.auth.unlock;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.presentation.base.BaseFragment;
import com.github.pwittchen.rxbiometric.library.validation.RxPreconditions;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UnlockFragment extends BaseFragment implements UnlockView {

    @Inject
    @InjectPresenter
    UnlockPresenter presenter;

    @BindView(R.id.edit_password)
    EditText passwordEdit;
    @BindView(R.id.button_unlock)
    Button unlockButton;
    @BindView(R.id.text_fingerprint)
    TextView fingerprintAuthButton;

    @ProvidePresenter
    UnlockPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle args) {
        presenter.addDisposable(RxView.clicks(unlockButton).subscribe(unit ->
                presenter.unlockDatabase(passwordEdit.getText().toString().toCharArray())));

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
        hideKeyboard();
        navigate(R.id.action_get_notes);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
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

    @Override
    public void showFingerprintUnlock() {
        presenter.addDisposable(RxPreconditions.hasBiometricSupport(requireContext())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(fingerprintSupported -> fingerprintSupported)
                .subscribe(fingerprintSupported -> {
                    fingerprintAuthButton.setVisibility(View.VISIBLE);
                    requireView().postDelayed(() ->
                                    presenter.fingerprintUnlock(requireActivity()),
                            500);
                }));
    }

    private void showFingerprintLock() {
        presenter.addDisposable(RxPreconditions.hasBiometricSupport(requireContext())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(fingerprintSupported -> fingerprintSupported)
                .subscribe(fingerprintSupported -> {
                    fingerprintAuthButton.setVisibility(View.VISIBLE);
                    requireView().postDelayed(() ->
                                    presenter.createFingerprintLock(requireActivity(),
                                            passwordEdit.getText().toString().toCharArray()),
                            500);
                }));
    }

    @Override
    public void showEnableFingerprint() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.title_use_fingerprint)
                .setMessage(R.string.message_fingerprint_unlock)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    dialog.dismiss();
                    showFingerprintLock();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    dialog.dismiss();
                    presenter.passwordUnlock(passwordEdit.getText().toString().toCharArray());
                })
                .create()
                .show();
    }
}
