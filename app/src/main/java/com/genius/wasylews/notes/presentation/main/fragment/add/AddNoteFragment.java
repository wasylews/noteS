package com.genius.wasylews.notes.presentation.main.fragment.add;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.presentation.base.BaseToolbarFragment;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;

public class AddNoteFragment extends BaseToolbarFragment implements AddNoteView {

    public static final String KEY_NOTE = "note_model";

    @Inject
    @InjectPresenter
    AddNotePresenter presenter;

    @BindView(R.id.edit_title) EditText noteTitleEdit;
    @BindView(R.id.edit_body) EditText noteBodyEdit;

    @ProvidePresenter
    AddNotePresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle args) {
        super.onViewReady(args);
        getBaseActivity().getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        presenter.addDisposable(RxTextView.textChanges(noteTitleEdit)
                .subscribe(text -> presenter.titleChanged(text.toString())));

        presenter.addDisposable(RxTextView.textChanges(noteBodyEdit)
                .subscribe(text -> presenter.bodyChanged(text.toString())));

        handleArgs(args);
        presenter.loadNote();
    }

    private void handleArgs(Bundle args) {
        if (args != null) {
            NoteModel note = (NoteModel) args.getSerializable(KEY_NOTE);
            if (note != null) {
                presenter.setNote(note);
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_add;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void goBack() {
        back();
    }

    @Override
    public void showMessage(String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showNote(NoteModel note) {
        noteTitleEdit.setText(note.getTitle());
        noteBodyEdit.setText(note.getText());
        if (TextUtils.isEmpty(note.getText())) {
            showKeyboard(noteBodyEdit);
        }
    }

    @Override
    protected boolean onBackPressed() {
        hideKeyboard();
        presenter.saveNote();
        return true;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
    }

    private void showKeyboard(EditText view) {
        view.post(() -> {
            view.requestFocus();
            view.setSelection(view.getText().length());
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        });
    }
}
