package com.ep.simpletodo.database;

public class TasksTable {
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "taskId";
    public static final String COLUMN_NAME = "taskName";
    public static final String COLUMN_NOTE = "notes";
    public static final String COLUMN_HASDATE = "hasDate";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_TASKS + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_NOTE + " TEXT," +
                    COLUMN_HASDATE + " BOOLEAN," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_TIME + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_TASKS;
}
