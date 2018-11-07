package com.ep.simpletodo.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ep.simpletodo.Todo;

import java.util.List;

public class TodoRepository {
    public TodoDao mTodoDao;
    private LiveData<List<Todo>> mAllTodo;

    TodoRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mTodoDao = db.todoDao();
        mAllTodo = mTodoDao.getAll();
    }

    LiveData<List<Todo>> getAllTodos() {
        return mAllTodo;
    }

    public void insert(Todo todo) {
        new insertAsyncTask(mTodoDao).execute(todo);
    }

    private static class insertAsyncTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao mAsyncTaskDao;

        public insertAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Todo... todos) {
            mAsyncTaskDao.insertAll(todos[0]);
            return null;
        }
    }
}
