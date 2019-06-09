package com.genius.wasylews.notes.domain.usecase.notes;

import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.base.single.SingleAsyncUseCase;
import com.genius.wasylews.notes.domain.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetNotesUseCase extends SingleAsyncUseCase<List<NoteModel>> {

    private NoteRepository repository;

    @Inject
    public GetNotesUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Single<List<NoteModel>> buildTask() {
        return repository.getNotes();
    }
}
