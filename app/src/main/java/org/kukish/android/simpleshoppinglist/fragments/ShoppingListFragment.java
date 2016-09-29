package org.kukish.android.simpleshoppinglist.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.kukish.android.simpleshoppinglist.listeners.OnItemSelectedListener;
import org.kukish.android.simpleshoppinglist.listeners.OnNewItemAddedListener;

/**
 * Created by Alejandro Awesome on 9/26/2016.
 */

public class ShoppingListFragment extends ListFragment {

    OnItemSelectedListener onItemSelectedListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedListener.onItemLongClick(position);
                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            if (context instanceof OnNewItemAddedListener)
                onItemSelectedListener = (OnItemSelectedListener) context;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        onItemSelectedListener.onItemSelected(position);
    }
}
