package com.genius.wasylews.notes.presentation.main.fragment.list;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.usecase.GetNotesUseCase;
import com.genius.wasylews.notes.domain.usecase.RemoveNoteUseCase;
import com.genius.wasylews.notes.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

@InjectViewState
public class NoteListPresenter extends BasePresenter<NoteListView> {

    private GetNotesUseCase getNotesUseCase;
    private RemoveNoteUseCase removeNoteUseCase;

    @Inject
    public NoteListPresenter(GetNotesUseCase getNotesUseCase,
                             RemoveNoteUseCase removeNoteUseCase) {
        super();
        this.getNotesUseCase = getNotesUseCase;
        this.removeNoteUseCase = removeNoteUseCase;
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

    public void removeNote(NoteModel note) {
        addDisposable(removeNoteUseCase.with(note).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getViewState().showNoteRemoved(note);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showMessage(e.getMessage());
            }
        }));
    }
}
