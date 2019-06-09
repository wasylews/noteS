package com.genius.wasylews.notes.domain.usecase.notes;

import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.base.single.SingleAsyncUseCase;
import com.genius.wasylews.notes.domain.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class SearchNotesUseCase extends SingleAsyncUseCase<List<NoteModel>> {

    private NoteRepository repository;
    private String query;

    @Inject
    public SearchNotesUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public SearchNotesUseCase with(String query) {
        this.query = query;
        return this;
    }

    @Override
    protected Single<List<NoteModel>> buildTask() {
        return repository.searchNotes(query);
    }
}
