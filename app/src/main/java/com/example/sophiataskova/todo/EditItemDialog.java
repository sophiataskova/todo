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
import android.widget.TextView;

public class EditItemDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;
    private Button mDoneButton;

    public interface EditItemDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public EditItemDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditItemDialog newInstance(String title, String prefill) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("prefill", prefill);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_content, container);
        mEditText = (EditText) view.findViewById(R.id.txt_new_content);
        mDoneButton = (Button) view.findViewById(R.id.done_button);

        String title = getArguments().getString("title", getResources().getString(R.string.edit_item_label));
        String prefill = getArguments().getString("prefill", "");
        getDialog().setTitle(title);
        mEditText.setText(prefill);
        mEditText.requestFocus();
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

        listener.onFinishEditDialog(mEditText.getText().toString());
        ((MainActivity)getActivity()).dismissKeyboard();
        dismiss();

    }
}