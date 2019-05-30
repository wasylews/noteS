package com.genius.wasylews.notes.presentation.main.fragment.add;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.presentation.base.BaseToolbarFragment;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;

public class AddNoteFragment extends BaseToolbarFragment implements AddNoteView {

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
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_add;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    protected boolean onBackPressed() {
        presenter.saveNote();
        return true;
    }
}
