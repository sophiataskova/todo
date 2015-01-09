package com.example.sophiataskova.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomToDoItemAdapter extends ArrayAdapter<TodoItem> {
    public CustomToDoItemAdapter(Context context, List<TodoItem> items) {
        super(context, 0, items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView tvContent = (TextView) convertView.findViewById(R.id.item_content);
        tvContent.setText(item.getContent());
        return convertView;
    }
}
