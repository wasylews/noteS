package com.genius.wasylews.notes.domain.usecase;

import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.base.single.SingleAsyncUseCase;
import com.genius.wasylews.notes.domain.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetNotesUseCase extends SingleAsyncUseCase<List<NoteModel>> {

    private NoteRepository repository;
    private char[] password;

    @Inject
    public GetNotesUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public GetNotesUseCase with(char[] password) {
        this.password = password;
        return this;
    }

    @Override
    protected Single<List<NoteModel>> buildTask() {
        return repository.getNotes(password);
    }
}
