package com.genius.wasylews.notes.presentation.main.fragment.add;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.usecase.AddNoteUseCase;
import com.genius.wasylews.notes.presentation.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

@InjectViewState
public class AddNotePresenter extends BasePresenter<AddNoteView> {

    private AddNoteUseCase addNoteUseCase;
    private String title;
    private String body;

    @Inject
    public AddNotePresenter(AddNoteUseCase addNoteUseCase) {
        super();
        this.addNoteUseCase = addNoteUseCase;
    }

    public void saveNote() {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(body)) {
            getViewState().goBack();
        }

        NoteModel note = new NoteModel();
        note.setTitle(title);
        note.setText(body);

        addDisposable(addNoteUseCase.with(note).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getViewState().goBack();
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showMessage(e.getMessage());
            }
        }));
    }

    public void titleChanged(String title) {
        this.title = title;
    }

    public void bodyChanged(String body) {
        this.body = body;
    }
}
