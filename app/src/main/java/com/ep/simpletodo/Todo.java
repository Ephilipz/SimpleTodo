package com.ep.simpletodo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "todos")
public class Todo implements Parcelable {

    @PrimaryKey
    @NonNull
    public String task_id;
    @ColumnInfo(name = "name")
    public String todo_name;
    @ColumnInfo(name = "has_date")
    public boolean hasDate = false;
    @ColumnInfo(name = "checked")
    public Boolean isChecked = false;
    @ColumnInfo(name = "date")
    public String date = null;
    @ColumnInfo(name = "time")
    public String time = null;
    @ColumnInfo(name = "note")
    public String note = null;


    public Todo(String todo_name) {
        this.todo_name = todo_name;
        task_id = UUID.randomUUID().toString();
    }

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

    public String getTask_id() {
        return task_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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


