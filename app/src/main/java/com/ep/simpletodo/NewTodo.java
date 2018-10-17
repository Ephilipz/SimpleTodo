package com.ep.simpletodo;

import android.app.Activity;
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

public class NewTodo extends AppCompatActivity implements View.OnClickListener {
    public static final String TASK_NAME_ST = "task_name_string";
    public static final String TASK_NOTES_ST = "task_notes_string";
    public static final String TASK_DATE_ST = "task_date_string";
    public static final String TASK_TIME_ST = "task_time_string";
    public static final String TIME_CHECK_ST = "task_time_check_string";
    public static final String DATE_CHECK_ST = "task_date_check_string";

    private EditText taskName_et;
    private EditText taskNotes_et;
    public static final String dateFormat = "DD/MM/yyyy";
    public static final String timeFormat = "HH:mm";
    Todo todo = new Todo(null);
    private CheckBox timeCheck, dateCheck;
    private TimePicker timePicker;
    private DatePicker datePicker;

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

        taskName_et = findViewById(R.id.taskName_tv);
        taskNotes_et = findViewById(R.id.taskNotes_et);
        dateCheck = findViewById(R.id.date_cb);
        timeCheck = findViewById(R.id.time_cb);
        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);

        dateCheck.setOnClickListener(this);
        timeCheck.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_cb:
                datePicker.setVisibility(View.VISIBLE);
                todo.setHasDate(dateCheck.isChecked());
                return;
            case R.id.time_cb:
                timePicker.setVisibility(View.VISIBLE);
                todo.setHasTime(timeCheck.isChecked());

        }
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
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            String formattedDate = sdf.format(calendar.getTime());
            todo.setDate(formattedDate);
        }

        if (timeCheck.isChecked()) {
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            String formattedTime = new StringBuilder().append(hour).append(":").append(minute).toString();
            todo.setTime(formattedTime);
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(TASK_NAME_ST, taskName);
        returnIntent.putExtra(TASK_NOTES_ST, notes);
        if (timeCheck.isChecked())
            returnIntent.putExtra(TASK_TIME_ST, todo.getTime());
        if (dateCheck.isChecked())
            returnIntent.putExtra(TASK_DATE_ST, todo.getDate());
        returnIntent.putExtra(TIME_CHECK_ST, timeCheck.isChecked());
        returnIntent.putExtra(DATE_CHECK_ST, dateCheck.isChecked());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        return true;
    }
}
