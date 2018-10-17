package com.ep.simpletodo;


import android.app.SearchManager;
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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    //RecyclerView data initialization
    private RecyclerView mRecyclerView;
    private recyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Todo> todoList;
    private FloatingActionButton fab_add;

    //request code to retrieve the new task from NewTodo activity
    public static final int TODO_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);

        todoList = new ArrayList<>();

        todoList.add(new Todo("eat pizza"));
        todoList.add(new Todo("eat avocados"));

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
        mAdapter = new recyclerViewAdapter(todoList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TODO_REQUEST_CODE && resultCode == RESULT_OK) {
            String passedString = data.getStringExtra(NewTodo.TASK_NAME_ST);
            String notes = data.getStringExtra(NewTodo.TASK_NOTES_ST);
            boolean hasTime = data.getBooleanExtra(NewTodo.TIME_CHECK_ST, false);
            boolean hasDate = data.getBooleanExtra(NewTodo.DATE_CHECK_ST, false);
            String date = null;
            String time = null;
            if (hasDate)
                date = data.getStringExtra(NewTodo.TASK_DATE_ST);
            if (hasTime)
                time = data.getStringExtra(NewTodo.TASK_TIME_ST);
            newTodo(passedString, notes, hasTime, hasDate, date, time);
        }
    }

    private void newTodo(String passedString, String notes, boolean hasTime, boolean hasDate, String date, String time) {
        Todo todo = new Todo(passedString);
        todo.setHasDate(hasDate);
        todo.setHasTime(hasTime);
        if (hasDate && date != null) {
            date = convertDateFromat(NewTodo.dateFormat, "MMM-dd", date);
            todo.setDate(date);
        }
        if (hasTime && time != null) {
            time = convertDateFromat(NewTodo.timeFormat, "hh:mm a", time);
            todo.setTime(time);
        }
        todo.setNote(notes);
        todoList.add(todo);
        mAdapter.notifyDataSetChanged();
        if (notes.isEmpty())
            Toast.makeText(this, "no notes to be added", Toast.LENGTH_SHORT).show();
    }

    public String convertDateFromat(String oldFormat, String newFormat, String date) {
        SimpleDateFormat format = new SimpleDateFormat(oldFormat);
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat(newFormat);
        return format.format(newDate);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase().trim();
        List<Todo> newList = new ArrayList<>();

        for (Todo todo : todoList) {
            if (todo.getTodo_name().contains(userInput)) {
                newList.add(todo);
            }
        }
        mAdapter.updateList(newList);
        return true;
    }

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
