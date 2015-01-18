package com.example.sophiataskova.todo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomToDoItemAdapter extends ArrayAdapter<TodoItem> {

    private final Context mContext;

    public CustomToDoItemAdapter(Context context, List<TodoItem> items) {
        super(context, 0, items);
        mContext = context;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView tvContent = (TextView) convertView.findViewById(R.id.item_content);
        tvContent.setText(item.getContent());
        View bullet = convertView.findViewById(R.id.item_bullet);

        if (item.getPriority() == TodoItem.Priority.HIGH) {
            bullet.setBackground(mContext.getResources().getDrawable(R.drawable.pink_circle));
        } else if (item.getPriority() == TodoItem.Priority.MED) {
            bullet.setBackground(mContext.getResources().getDrawable(R.drawable.yellow_circle));
        } else {
            bullet.setBackground(mContext.getResources().getDrawable(R.drawable.green_circle));
        }

        TextView tvDueDate = (TextView) convertView.findViewById(R.id.item_due_date);
        String dueDateString = TodoItemDatabase.sdf.format(item.getDueDate());
        tvDueDate.setText(dueDateString);
        return convertView;
    }


}
