package com.ep.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates top app bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //get recycler view
        mRecyclerView = findViewById(R.id.recyclerView);

        //use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //get sample data list
        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo("buy groceries"));
        todoList.add(new Todo("send email to Luc"));
        todoList.add(new Todo("soccer practice"));


        //specify the adapter
        mAdapter = new recyclerViewAdapter(todoList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addTodo:
                startActivity(new Intent(this, NewTodo.class));
                return true;
            case R.id.action_settings:
                //TODO: open Settings activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
