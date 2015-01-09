package com.example.sophiataskova.todo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    List<TodoItem> items;
    List<String> itemsContents;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    TodoItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.listView);
        itemsContents = new ArrayList();
        db = new TodoItemDatabase(this);
        items = db.getAllItems();
        for (TodoItem item : items) {
            itemsContents.add(item.getContent());
        }
        itemsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsContents);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteItem(items.get(position));
                items.remove(position);
                itemsContents.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.editText);
        String itemText = etNewItem.getText().toString();
        if (!itemText.equals("")) {
            TodoItem itemToAdd = new TodoItem((itemText));
            db.addItem(itemToAdd);
            items.add(itemToAdd);
            itemsAdapter.add(itemToAdd.getContent());
            itemsAdapter.notifyDataSetChanged();
            etNewItem.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
        }
    }
}
