package com.example.sophiataskova.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TodoItemDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todoitemsdb";
    private static final int DATABASE_VERSION = 1;
    private static final String ITEMS_TABLE = "todoitemstable";

    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DUE_DATE = "due_date";
    private static final String KEY_PRIORITY = "priority";

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");


    public TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL for creating the tables
        String CREATE_TODO_TABLE = "CREATE TABLE " + ITEMS_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CONTENT + " TEXT," + KEY_PRIORITY + " TEXT," + KEY_DUE_DATE + " DATETIME" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    // This method is called when database is upgraded like
    // modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        // SQL for upgrading the tables
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE);
        onCreate(db);
    }

    // Adding new todo item
    public void addItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, item.getContent());
        values.put(KEY_DUE_DATE, sdf.format(item.getDueDate()));
        values.put(KEY_PRIORITY, TodoItem.Priority.LOW.name());

        db.insert(ITEMS_TABLE, null, values);
        db.close();
    }

    public List<TodoItem> getAllItems() {
        List<TodoItem> todoItems = new ArrayList<TodoItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ITEMS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TodoItem todoItem = new TodoItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                // Adding todoItem to list
                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }
        return todoItems;
    }

    public int updateItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, item.getContent());
        values.put(KEY_PRIORITY, item.getPriority().name());
        values.put(KEY_DUE_DATE, sdf.format(item.getDueDate()));

        // updating row
        return db.update(ITEMS_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
    }

    public void deleteItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ITEMS_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();
    }

}