package org.kukish.android.simpleshoppinglist;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.kukish.android.simpleshoppinglist.adapters.ShoppingItemAdapter;
import org.kukish.android.simpleshoppinglist.fragments.ShoppingListFragment;
import org.kukish.android.simpleshoppinglist.listeners.OnItemDeletedListener;
import org.kukish.android.simpleshoppinglist.listeners.OnNewItemAddedListener;

import java.util.ArrayList;

public class ShoppingListActivity extends Activity
        implements OnNewItemAddedListener, OnItemDeletedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private ShoppingItemAdapter adapter;
    private ArrayList<ShoppingItem> shoppingItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        FragmentManager fragmentManager = getFragmentManager();
        ShoppingListFragment shoppingListFragment =
                (ShoppingListFragment) fragmentManager.findFragmentById(R.id.shoppingListFragment);

        shoppingItems = new ArrayList<>();
        adapter = new ShoppingItemAdapter(this, R.layout.list_item, shoppingItems);

        shoppingListFragment.setListAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onNewItemAdded(String itemName, String quantity) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(ListContentProvider.KEY_NAME, itemName);
        values.put(ListContentProvider.KEY_QUANTITY, quantity);

        contentResolver.insert(ListContentProvider.CONTENT_URI, values);
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onItemSelected(int position) {
        shoppingItems.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                ListContentProvider.CONTENT_URI, null, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        int keyTaskIndex = cursor.getColumnIndexOrThrow(ListContentProvider.KEY_NAME);
        int keyquantIndex = cursor.getColumnIndexOrThrow(ListContentProvider.KEY_QUANTITY);

        shoppingItems.clear();

        while (cursor.moveToNext()) {
            ShoppingItem item = new ShoppingItem(cursor.getString(keyTaskIndex),
                    cursor.getString(keyquantIndex));
            shoppingItems.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
