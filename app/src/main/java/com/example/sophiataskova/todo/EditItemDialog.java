package com.example.sophiataskova.todo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class EditItemDialog extends DialogFragment implements TextView.OnEditorActionListener, View.OnClickListener{

    private EditText mEditText;
    private Button mDoneButton;
    private TodoItem.Priority mPriority;
    private RadioButton mLowPriorityButton;
    private RadioButton mMedPriorityButton;
    private RadioButton mHighPriorityButton;




    public interface EditItemDialogListener {
        void onFinishEditDialog(String inputText, TodoItem.Priority priority);
    }

    public EditItemDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditItemDialog newInstance(String title, String prefill, String priority) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("prefill", prefill);
        args.putString("priority", priority);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_content, container);
        mEditText = (EditText) view.findViewById(R.id.txt_new_content);
        mDoneButton = (Button) view.findViewById(R.id.done_button);

        mLowPriorityButton = (RadioButton) view.findViewById(R.id.priority_low);
        mMedPriorityButton = (RadioButton) view.findViewById(R.id.priority_mid);
        mHighPriorityButton = (RadioButton) view.findViewById(R.id.priority_high);

        String title = getArguments().getString("title", getResources().getString(R.string.edit_item_label));
        String prefill = getArguments().getString("prefill", "");
        TodoItem.Priority currentPriority = TodoItem.Priority.valueOf(getArguments().getString("priority", ""));
        if (currentPriority == TodoItem.Priority.LOW) {
            mLowPriorityButton.setChecked(true);
        } else if (currentPriority == TodoItem.Priority.MED) {
            mMedPriorityButton.setChecked(true);
        } else {
            mHighPriorityButton.setChecked(true);
        }
        setPriority(currentPriority);


        getDialog().setTitle(title);
        mEditText.setText(prefill);
        mEditText.requestFocus();

        mLowPriorityButton.setOnClickListener(this);
        mMedPriorityButton.setOnClickListener(this);
        mHighPriorityButton.setOnClickListener(this);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyContentChanges();
            }
        });
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            applyContentChanges();
            return true;
        }
        return false;
    }

    private void applyContentChanges() {
        EditItemDialogListener listener = (EditItemDialogListener) getActivity();

        listener.onFinishEditDialog(mEditText.getText().toString(), mPriority);
        ((MainActivity)getActivity()).dismissKeyboard();
        dismiss();

    }

    public void setPriority(TodoItem.Priority priority) {
        mPriority = priority;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.priority_low: setPriority(TodoItem.Priority.LOW);
                break;
            case R.id.priority_mid: setPriority(TodoItem.Priority.MED);
                break;
            case R.id.priority_high: setPriority(TodoItem.Priority.HIGH);
                break;
        }
    }
}