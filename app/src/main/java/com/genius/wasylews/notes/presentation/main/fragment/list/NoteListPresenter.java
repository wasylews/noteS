package com.genius.wasylews.notes.presentation.main.fragment.list;

import com.arellomobile.mvp.InjectViewState;
import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.usecase.GetNotesUseCase;
import com.genius.wasylews.notes.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

@InjectViewState
public class NoteListPresenter extends BasePresenter<NoteListView> {

    private GetNotesUseCase getNotesUseCase;

    @Inject
    public NoteListPresenter(GetNotesUseCase getNotesUseCase) {
        super();
        this.getNotesUseCase = getNotesUseCase;
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
}
