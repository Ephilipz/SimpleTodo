package com.ep.simpletodo.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.ep.simpletodo.Todo;

@Database(entities = {Todo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static RoomDatabase.Callback sRoomDataCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "todo-database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDataCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    public abstract TodoDao todoDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TodoDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Todo todo = new Todo("Sample 1");
            mDao.insertAll(todo);
            return null;
        }
    }

}
