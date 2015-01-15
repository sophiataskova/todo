package com.example.sophiataskova.todo;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity  implements EditItemDialog.EditItemDialogListener {

    private List<TodoItem> items;
    private CustomToDoItemAdapter itemsAdapter;
    private ListView lvItems;
    private TodoItemDatabase db;
    private int mItemPosition;
    private Date mItemDate;
    private String mItemContent;
    private EditText etNewItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.listView);
        db = new TodoItemDatabase(this);
        items = db.getAllItems();
        itemsAdapter = new CustomToDoItemAdapter(this, items);
        etNewItem = (EditText) findViewById(R.id.editText);
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
                mItemPosition = position;
                showEditDialog(items.get(position).getContent());
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

        mItemContent = etNewItem.getText().toString();

        if (!mItemContent.equals("")) {
            mItemDate = new Date(System.currentTimeMillis());

           showDatePickerDialog(v);
        }
        else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.new_item_empty_string_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertItem(EditText etNewItem, String itemText) {
        TodoItem itemToAdd = new TodoItem(itemText, TodoItemDatabase.sdf.format(mItemDate));
        addTodoItem(etNewItem, itemToAdd);
    }

    private void addTodoItem(EditText etNewItem, TodoItem itemToAdd) {
        db.addItem(itemToAdd);
        items.add(itemToAdd);
        itemsAdapter.notifyDataSetChanged();
        etNewItem.setText("");
    }

    private void showEditDialog(String prefillText) {
        EditItemDialog editNameDialog = EditItemDialog.newInstance(getResources().getString(R.string.edit_item_label), prefillText);
        editNameDialog.show(getFragmentManager(), "fragment_edit_content");
    }

    private void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        if (inputText.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.edit_empty_string_error), Toast.LENGTH_SHORT).show();
        } else {
            TodoItem itemBeingEdited = items.get(mItemPosition);
            itemBeingEdited.setContent(inputText);
            db.updateItem(itemBeingEdited);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    public void insertItemWithDueDate (int year, int month, int day) {
        mItemDate = new Date(year,month,day);
        insertItem(etNewItem, mItemContent);

        dismissKeyboard();
    }

    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etNewItem.getWindowToken(), 0);
    }


}
