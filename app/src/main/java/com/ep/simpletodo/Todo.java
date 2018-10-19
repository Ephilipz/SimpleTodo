package com.ep.simpletodo;

import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {
    private String todo_name;
    private Boolean isChecked = false;
    private String date;
    private String time;
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

    public boolean isHasTime() {
        return hasTime;
    }

    public void setHasTime(boolean hasTime) {
        this.hasTime = hasTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel source) {
            return new Todo(source);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    protected Todo(Parcel in) {
        this.todo_name = in.readString();
        this.isChecked = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.date = in.readString();
        this.time = in.readString();
        this.hasDate = in.readByte() != 0;
        this.hasTime = in.readByte() != 0;
        this.note = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.todo_name);
        dest.writeValue(this.isChecked);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeByte(this.hasDate ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasTime ? (byte) 1 : (byte) 0);
        dest.writeString(this.note);
    }
}
