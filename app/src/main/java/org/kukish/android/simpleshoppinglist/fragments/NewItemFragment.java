package org.kukish.android.simpleshoppinglist.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.kukish.android.simpleshoppinglist.R;
import org.kukish.android.simpleshoppinglist.listeners.OnNewItemAddedListener;

/**
 * Created by Alejandro Awesome on 9/26/2016.
 */

public class NewItemFragment extends Fragment {

    private OnNewItemAddedListener onNewItemAddedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_item_fragment, container, false);

        final EditText itemNameEditText = (EditText) view.findViewById(R.id.itemName);
        final EditText itemNameQuantity = (EditText) view.findViewById(R.id.itemQuanity);

        itemNameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return onKeyCheck(keyCode, event, itemNameEditText, itemNameQuantity);
            }
        });

        itemNameQuantity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return onKeyCheck(keyCode, event, itemNameEditText, itemNameQuantity);
            }
        });

        return view;
    }

    private boolean onKeyCheck(int keyCode, KeyEvent event, EditText itemNameEditText, EditText itemNameQuantity) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
            if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String newItem = itemNameEditText.getText().toString();
                if (newItem.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), "please specify item", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.START, 0, 0);
                    toast.show();
                    return false;
                }
                String quantity = itemNameQuantity.getText().toString();
                onNewItemAddedListener.onNewItemAdded(newItem, quantity);
                itemNameEditText.setText("");
                return true;
            }
        return false;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            if (context instanceof OnNewItemAddedListener)
                onNewItemAddedListener = (OnNewItemAddedListener) context;
    }
}
