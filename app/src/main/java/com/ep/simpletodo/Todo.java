package com.ep.simpletodo;

public class Todo {
    private String todo_name;
    private String isChecked;

    public Todo(String todo_name) {
        this.todo_name = todo_name;
    }

    public String getTodo_name() {
        return todo_name;
    }

    public void setTodo_name(String todo_name) {
        this.todo_name = todo_name;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
