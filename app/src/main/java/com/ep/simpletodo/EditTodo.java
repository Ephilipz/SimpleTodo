package com.ep.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTodo extends AppCompatActivity implements View.OnClickListener {
    public static final String deleteItemID = "DELETE_ITEM_ID";
    public static final String EDIT_TASK_ID = "EDITED_TASK_ID";
    Intent intent;
    Todo todo;
    private EditText taskName_et;
    private EditText taskNotes_et;
    private CheckBox dateCheck;
    private TimePicker timePicker;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        //get toolbar
        Toolbar newTodoToolbar = findViewById(R.id.newTodo_toolbar);
        setSupportActionBar(newTodoToolbar);

        //add "up" button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //instantiate objects on screen
        taskName_et = findViewById(R.id.taskName_tv);
        taskNotes_et = findViewById(R.id.taskNotes_et);
        dateCheck = findViewById(R.id.date_cb);
        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);

        dateCheck.setOnClickListener(this);

        intent = new Intent(this, MainActivity.class);
        todo = getIntent().getParcelableExtra(NewTodo.TASK_ID);

        //set the widgets to the tasks's information
        taskName_et.setText(todo.getTodo_name());
        taskNotes_et.setText(todo.getNote());
        dateCheck.setChecked(todo.isHasDate());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                return editTodo();
            case R.id.action_deleteItem:
                return deleteItem();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean deleteItem() {
        intent.putExtra(deleteItemID, true);
        return true;
    }

    private boolean editTodo() {
        Calendar calendar = Calendar.getInstance();
        String taskName = taskName_et.getText().toString()
                .trim().replaceAll(" +", " ");
        String notes = taskNotes_et.getText().toString();
        if (taskName.isEmpty()) {
            taskName_et.setError("Task name cannot be empty");
            return false;
        }
        todo.setTodo_name(taskName);
        todo.setNote(notes);
        if (dateCheck.isChecked()) {
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            int day = datePicker.getDayOfMonth();
            calendar.set(year, month, day);
            SimpleDateFormat sdf = new SimpleDateFormat(NewTodo.dateFormat);
            String formattedDate = sdf.format(calendar.getTime());
            todo.setDate(formattedDate);
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            String formattedTime = new StringBuilder().append(hour).append(":").append(minute).toString();
            todo.setTime(formattedTime);
            todo.setHasDate(true);
        }

        intent.putExtra(EDIT_TASK_ID, todo);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_cb:
                if (dateCheck.isChecked()) {
                    datePicker.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                    todo.setHasDate(true);
                } else {
                    datePicker.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    todo.setHasDate(false);
                }
        }
    }
}
