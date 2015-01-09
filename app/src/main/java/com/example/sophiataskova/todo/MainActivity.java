package com.example.sophiataskova.todo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity  implements EditItemDialog.EditItemDialogListener {

    private List<TodoItem> items;
    private CustomToDoItemAdapter itemsAdapter;
    private ListView lvItems;
    private TodoItemDatabase db;
    private int positionToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.listView);
        db = new TodoItemDatabase(this);
        items = db.getAllItems();
        itemsAdapter = new CustomToDoItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteItem(items.get(position));
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionToEdit = position;
                showEditDialog();
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
            itemsAdapter.notifyDataSetChanged();
            etNewItem.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
        }
    }
    private void showEditDialog() {
        EditItemDialog editNameDialog = EditItemDialog.newInstance("Edit item");
        editNameDialog.show(getFragmentManager(), "fragment_edit_content");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        if (inputText.equals("")) {
            Toast.makeText(this, "Please enter a nonempty string! To delete an item, long-press", Toast.LENGTH_SHORT).show();
        } else {
            TodoItem itemBeingEdited = items.get(positionToEdit);
            itemBeingEdited.setContent(inputText);
            db.updateItem(itemBeingEdited);
            itemsAdapter.notifyDataSetChanged();
        }

    }
}
