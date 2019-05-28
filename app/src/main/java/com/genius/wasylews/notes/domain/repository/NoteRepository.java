package com.genius.wasylews.notes.domain.repository;

import com.genius.wasylews.notes.data.db.AppDatabase;
import com.genius.wasylews.notes.data.db.NoteDao;
import com.genius.wasylews.notes.data.db.model.NoteModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class NoteRepository {

    private AppDatabase.Factory dbFactory;
    private NoteDao noteDao;

    @Inject
    public NoteRepository(AppDatabase.Factory dbFactory) {
        this.dbFactory = dbFactory;
    }

    public Single<List<NoteModel>> getNotes(char[] password) {
        noteDao = dbFactory.create(password).getNoteDao();
        return noteDao.getNotes();
    }

    public Completable addNote(NoteModel note) {
        return noteDao.addNote(note);
    }

    public Completable updateNote(NoteModel note) {
        return noteDao.updateNote(note);
    }

    public Completable removeNote(NoteModel note) {
        return noteDao.removeNote(note);
    }
}
