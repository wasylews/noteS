package com.genius.wasylews.notes.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.genius.wasylews.notes.data.db.model.NoteModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface NoteDao {

    @Query("select * from notes")
    Single<List<NoteModel>> getNotes();

    @Insert
    Completable addNote(NoteModel note);

    @Update
    Completable updateNote(NoteModel note);

    @Delete
    Completable removeNote(NoteModel note);

    @Query("select * from notes where title like '%' || :query || '%' or text like '%' || :query || '%'")
    Single<List<NoteModel>> searchNotes(String query);
}
