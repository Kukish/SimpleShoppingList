package org.kukish.android.simpleshoppinglist.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

import org.kukish.android.simpleshoppinglist.listeners.OnItemDeletedListener;
import org.kukish.android.simpleshoppinglist.listeners.OnNewItemAddedListener;

/**
 * Created by Alejandro Awesome on 9/26/2016.
 */

public class ShoppingListFragment extends ListFragment {

    OnItemDeletedListener onItemDeletedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            if (context instanceof OnNewItemAddedListener)
                onItemDeletedListener = (OnItemDeletedListener) context;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        onItemDeletedListener.onItemSelected(position);
    }
}
