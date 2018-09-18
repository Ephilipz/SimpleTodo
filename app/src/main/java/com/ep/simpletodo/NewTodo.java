package com.ep.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class NewTodo extends AppCompatActivity {
    public static final String TASK_NAME_ST = "task_name_string";
    private EditText taskName_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);

        //get toolbar
        Toolbar newTodoToolbar = findViewById(R.id.newTodo_toolbar);
        setSupportActionBar(newTodoToolbar);

        //add "up" button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //set taskName to new task name from the EditText
        taskName_et = findViewById(R.id.taskName_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                return addTodo();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean addTodo() {
        String taskName = taskName_et.getText().toString().trim().replaceAll(" +", " ");
        if (taskName.isEmpty()) {
            taskName_et.setError("Task name cannot be empty");
            return false;
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra(TASK_NAME_ST, taskName);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        return true;
    }
}
