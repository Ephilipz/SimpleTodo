package com.ep.simpletodo;


import android.app.Activity;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ep.simpletodo.database.AppDatabase;
import com.ep.simpletodo.database.TodoViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eesaa Philips
 * @version 1.0
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    //RecyclerView data initialization
    private RecyclerView mRecyclerView;
    private recyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab_add;

    public static TodoViewModel mTodoViewModel;

    //request code to retrieve the new task from NewTodo activity
    public static final int TODO_REQUEST_CODE = 1;

    //request code to edit the task from EditTodo activity
    public static final int EDIT_TODO_REQUEST_CODE = 2;

    /**
     * This onCreate method is started automatically when the app starts
     *
     * @return null
     * @param: savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);

        //creates top app bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //get recycler view
        mRecyclerView = findViewById(R.id.recyclerView);

        //use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //specify the adapter
        mAdapter = new recyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);

        mTodoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todoList) {
                mAdapter.updateList(todoList);
            }
        });

    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();

    }

    /**
     * Instantiates the main menu on the toolbar
     *
     * @param menu : the menu to be instantiated
     * @return boolean: whether the menu was created or not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        //Search functionality
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(this);
        return true;
    }

    /**
     * Handles clicks on menu items
     *
     * @param item : the clicked item
     * @return boolean whether the click was handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_addTodo:
                startActivityForResult(new Intent(this, NewTodo.class), TODO_REQUEST_CODE);
                return true;
            case R.id.action_settings:
                //TODO: open Settings activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Handles received data from {@link NewTodo} and {@link EditTodo}
     *
     * @param requestCode : the request code used to retrieve the data
     * @param resultCode  : the result code used to identify the type of result passed by the other activity
     * @param data        : the passed data from the other activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //checks of the request code is from NewTodo
        if (requestCode == TODO_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            Todo todo = data.getParcelableExtra(NewTodo.TASK_ID);
            //adds retrieved task to the todoList
            mTodoViewModel.insert(todo);
        }

        //check if the request code is from EditTodo
        else if (requestCode == EDIT_TODO_REQUEST_CODE) {
            assert data != null;
            Todo todo = data.getParcelableExtra(EditTodo.EDIT_TASK_ID);
            if (resultCode == EditTodo.RESULT_DELETE) {
                mTodoViewModel.delete(todo);
                mAdapter.notifyDataSetChanged();
            } else if (resultCode == Activity.RESULT_OK)
                mTodoViewModel.update(todo);
        }
    }


    /**
     * Handles search result if enter is clicked
     *
     * @param s : String that user submit
     * @return false : results only show while typing
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    /**
     * Handles text change in search and updates the recycler view adapter filter
     *
     * @param s : String that user submit
     * @return true : when the list is updated
     */
    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase().trim();
        List<Todo> newList = new ArrayList<>();

        for (Todo todo : mTodoViewModel.getAllTodos().getValue()) {
            if (todo.getTodo_name().contains(userInput)) {
                newList.add(todo);
            }
        }
        mAdapter.updateList(newList);
        return true;
    }

    /**
     * Handles clicks
     *
     * @param view : the view that was clicked
     * @see View.OnClickListener
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add:
                startActivityForResult(new Intent(this, NewTodo.class), TODO_REQUEST_CODE);
            default:
                break;
        }
    }
}