package com.genius.wasylews.notes.domain.repository;

import com.genius.wasylews.notes.data.db.AppDatabase;
import com.genius.wasylews.notes.data.db.NoteDao;
import com.genius.wasylews.notes.data.db.model.NoteModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class NoteRepository {

    private AppDatabase.Factory dbFactory;
    private NoteDao noteDao;

    @Inject
    public NoteRepository(AppDatabase.Factory dbFactory) {
        this.dbFactory = dbFactory;
    }

    private Single<NoteDao> getDao() {
        if (noteDao == null) {
            return Single.error(new Exception("dao is not initialized"));
        }
        return Single.just(noteDao);
    }

    public Completable openDatabase(char[] password) {
        noteDao = dbFactory.create(password).getNoteDao();
        return Completable.complete();
    }

    public Single<List<NoteModel>> getNotes() {
        return getDao().flatMap(NoteDao::getNotes);
    }

    public Completable addNote(NoteModel note) {
        return getDao().flatMapCompletable(dao -> dao.addNote(note));
    }

    public Completable updateNote(NoteModel note) {
        return getDao().flatMapCompletable(dao -> dao.updateNote(note));
    }

    public Completable removeNote(NoteModel note) {
        return getDao().flatMapCompletable(dao -> dao.removeNote(note));
    }
}
