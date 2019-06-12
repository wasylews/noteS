package com.genius.wasylews.notes.presentation.main.fragment.add;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.usecase.notes.AddNoteUseCase;
import com.genius.wasylews.notes.domain.usecase.notes.UpdateNoteUseCase;
import com.genius.wasylews.notes.presentation.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

@InjectViewState
public class AddNotePresenter extends BasePresenter<AddNoteView> {

    private AddNoteUseCase addNoteUseCase;
    private UpdateNoteUseCase updateNoteUseCase;
    private String title;
    private String body;
    private NoteModel note = new NoteModel();

    @Inject
    public AddNotePresenter(AddNoteUseCase addNoteUseCase,
                            UpdateNoteUseCase updateNoteUseCase) {
        super();
        this.addNoteUseCase = addNoteUseCase;
        this.updateNoteUseCase = updateNoteUseCase;
    }

    public void saveNote() {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(body)) {
            getViewState().goBack();
            return;
        }

        note.setTitle(title);
        note.setText(body);

        if (note.getId() == null) {
            addNote();
        } else {
            updateNote();
        }
    }

    private void updateNote() {
        addDisposable(updateNoteUseCase.with(note).execute(new DisposableCompletableObserver() {
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

    private void addNote() {
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

    public void setNote(NoteModel note) {
        this.note = note;
    }

    public void loadNote() {
        getViewState().showNote(note);
    }
}
