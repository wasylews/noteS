package com.genius.wasylews.notes.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.genius.wasylews.notes.data.db.model.NoteModel;

import javax.inject.Inject;

@Database(entities = {NoteModel.class}, version = AppDatabase.VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "notes.db";
    static final int VERSION = 1;

    public abstract NoteDao getNoteDao();

    public static class Factory {
        private Context context;

        @Inject
        public Factory(Context context) {
            this.context = context;
        }

        public AppDatabase create(char[] password) {
            SafeHelperFactory factory = new SafeHelperFactory(password);
            return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .openHelperFactory(factory)
                    .build();
        }
    }
}
