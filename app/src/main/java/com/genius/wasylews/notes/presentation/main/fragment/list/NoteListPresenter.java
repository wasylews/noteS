package com.genius.wasylews.notes.presentation.main.fragment.list;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.usecase.notes.AddNoteUseCase;
import com.genius.wasylews.notes.domain.usecase.notes.GetNotesUseCase;
import com.genius.wasylews.notes.domain.usecase.notes.RemoveNoteUseCase;
import com.genius.wasylews.notes.domain.usecase.notes.SearchNotesUseCase;
import com.genius.wasylews.notes.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

@InjectViewState
public class NoteListPresenter extends BasePresenter<NoteListView> {

    private GetNotesUseCase getNotesUseCase;
    private AddNoteUseCase addNoteUseCase;
    private RemoveNoteUseCase removeNoteUseCase;
    private SearchNotesUseCase searchNotesUseCase;

    @Inject
    public NoteListPresenter(GetNotesUseCase getNotesUseCase,
                             AddNoteUseCase addNoteUseCase,
                             RemoveNoteUseCase removeNoteUseCase,
                             SearchNotesUseCase searchNotesUseCase) {
        super();
        this.getNotesUseCase = getNotesUseCase;
        this.addNoteUseCase = addNoteUseCase;
        this.removeNoteUseCase = removeNoteUseCase;
        this.searchNotesUseCase = searchNotesUseCase;
    }

   public void loadData() {
       addDisposable(getNotesUseCase.execute(new DisposableSingleObserver<List<NoteModel>>() {
           @Override
           public void onSuccess(List<NoteModel> noteModels) {
               getViewState().showNotes(noteModels);
           }

           @Override
           public void onError(Throwable e) {
               getViewState().showMessage(e.getMessage());
           }
       }));
   }

    public void removeNote(NoteModel note, int position) {
        addDisposable(removeNoteUseCase.with(note).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getViewState().showNoteRemoved(note, position);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showMessage(e.getMessage());
            }
        }));
    }

    public void insertNote(NoteModel note, int position) {
        addDisposable(addNoteUseCase.with(note).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getViewState().showNoteInserted(note, position);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showMessage(e.getMessage());
            }
        }));
    }

    public void searchNotes(String query) {
        if (TextUtils.isEmpty(query)) {
            loadData();
            return;
        }
        addDisposable(searchNotesUseCase.with(query).execute(new DisposableSingleObserver<List<NoteModel>>() {
            @Override
            public void onSuccess(List<NoteModel> noteModels) {
                getViewState().showNotes(noteModels);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showMessage(e.getMessage());
            }
        }));
    }
}
