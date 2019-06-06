package com.genius.wasylews.notes.domain.usecase;

import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.domain.base.completable.CompletableAsyncUseCase;
import com.genius.wasylews.notes.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class RemoveNoteUseCase extends CompletableAsyncUseCase {

    private NoteRepository repository;
    private NoteModel note;

    @Inject
    public RemoveNoteUseCase(NoteRepository repository) {
        this.repository = repository;
    }

    public RemoveNoteUseCase with(NoteModel note) {
        this.note = note;
        return this;
    }

    @Override
    protected Completable buildTask() {
        return repository.removeNote(note);
    }
}
