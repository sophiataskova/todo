package com.example.sophiataskova.todo;

import java.text.ParseException;
import java.util.Date;

public class TodoItem {

    private int mId;
    private Date mDueDate;
    private String mContent;

    public TodoItem(final int id, final String content, final String date) {
        mId = id;
        mContent = content;
        try {
            mDueDate = TodoItemDatabase.sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TodoItem(final String content, String date) {
        mContent = content;
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

    public void setDate(Date dueDate) {
        mDueDate = dueDate;
    }
}
