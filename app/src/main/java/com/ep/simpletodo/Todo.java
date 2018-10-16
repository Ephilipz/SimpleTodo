package com.ep.simpletodo;


import java.util.Date;

public class Todo {
    private String todo_name;
    private Boolean isChecked = false;
    private Date date;
    private boolean hasDate;
    private boolean hasTime;
    private String note;

    public Todo(String todo_name) {
        this.todo_name = todo_name;
    }

    public String getTodo_name() {
        return todo_name;
    }

    public void setTodo_name(String todo_name) {
        this.todo_name = todo_name;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
