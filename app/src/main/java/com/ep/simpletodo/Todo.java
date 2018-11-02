package com.ep.simpletodo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Todo implements Parcelable {
    private String todo_name;
    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel source) {
            return new Todo(source);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
    private Boolean isChecked = false;
    private String date;
    private String time;
    private boolean hasDate;
    private String note;
    private String task_id;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Todo(String todo_name) {
        this.todo_name = todo_name;
        this.task_id = UUID.randomUUID().toString();
    }


    protected Todo(Parcel in) {
        this.todo_name = in.readString();
        this.task_id = in.readString();
        this.isChecked = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.date = in.readString();
        this.time = in.readString();
        this.hasDate = in.readByte() != 0;
        this.note = in.readString();
    }

    public String getTask_id() {
        return task_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.todo_name);
        dest.writeString(this.task_id);
        dest.writeValue(this.isChecked);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeByte(this.hasDate ? (byte) 1 : (byte) 0);
        dest.writeString(this.note);
    }
}
