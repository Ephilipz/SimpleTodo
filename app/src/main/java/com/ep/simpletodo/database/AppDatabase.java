package com.ep.simpletodo.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ep.simpletodo.Todo;

@Database(entities = {Todo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static RoomDatabase.Callback sRoomDataCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "todo-database")
                            .allowMainThreadQueries()
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

}
