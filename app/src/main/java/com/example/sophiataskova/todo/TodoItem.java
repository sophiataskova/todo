package com.example.sophiataskova.todo;

public class TodoItem {

    private int mId;
    private String mContent;

    public TodoItem(final int id, final String content) {
        mId = id;
        mContent = content;
    }

    public TodoItem(final String content) {
        mContent = content;
    }

    public int getId() {
        return mId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
