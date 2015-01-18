package com.example.sophiataskova.todo;

import java.text.ParseException;
import java.util.Date;

public class TodoItem {

    private int mId;
    private Date mDueDate;
    private String mContent;
    private Priority mPriority;

    public enum Priority {
        LOW, MED, HIGH
    }

    public TodoItem(final int id, final String content, final String priority, final String date) {
        mId = id;
        mContent = content;

        mPriority = Priority.valueOf(priority);
        try {
            mDueDate = TodoItemDatabase.sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TodoItem(final String content, final String priority, String date) {
        mContent = content;
        mPriority = Priority.valueOf(priority);
        try {
            mDueDate = TodoItemDatabase.sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return mId;
    }

    public String getContent() {
        return mContent;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    public Priority getPriority() {
        return mPriority;
    }
}
