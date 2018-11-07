package com.ep.simpletodo.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ep.simpletodo.Todo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mRepository;

    private LiveData<List<Todo>> mAllTodos;

    public TodoViewModel(Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mAllTodos = mRepository.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    public void insert(Todo todo) {
        mRepository.insert(todo);
    }

    public void delete(Todo todo) {
        mRepository.mTodoDao.delete(todo);
    }

    public void update(Todo todo) {
        mRepository.mTodoDao.update(todo);
    }
}
