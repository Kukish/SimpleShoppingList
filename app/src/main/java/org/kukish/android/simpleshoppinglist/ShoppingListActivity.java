package org.kukish.android.simpleshoppinglist;

/**
 * Created by Alejandro Awesome on 9/27/2016.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import org.kukish.android.simpleshoppinglist.adapters.ShoppingItemAdapter;
import org.kukish.android.simpleshoppinglist.fragments.ShoppingListFragment;
import org.kukish.android.simpleshoppinglist.listeners.OnItemDeletedListener;
import org.kukish.android.simpleshoppinglist.listeners.OnNewItemAddedListener;

import java.util.ArrayList;

import static org.kukish.android.simpleshoppinglist.ListContentProvider.KEY_ID;
import static org.kukish.android.simpleshoppinglist.ListContentProvider.KEY_NAME;
import static org.kukish.android.simpleshoppinglist.ListContentProvider.KEY_QUANTITY;

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
        values.put(KEY_NAME, itemName);
        values.put(KEY_QUANTITY, quantity);

        contentResolver.insert(ListContentProvider.CONTENT_URI, values);
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onItemSelected(int position) {
        ContentResolver contentResolver = getContentResolver();
        ShoppingItem itemSelected = shoppingItems.get(position);
        String selection = KEY_ID + " = '" + itemSelected.getUid() + "'";
        contentResolver.delete(ListContentProvider.CONTENT_URI, selection, null);
        getLoaderManager().restartLoader(0, null, this);
        Toast toast = Toast.makeText(this, itemSelected.getName() + " deleted sucessfully", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                ListContentProvider.CONTENT_URI, null, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        int keyNameIndex = cursor.getColumnIndexOrThrow(KEY_NAME);
        int keyquantIndex = cursor.getColumnIndexOrThrow(KEY_QUANTITY);
        int keyId = cursor.getColumnIndexOrThrow(KEY_ID);

        shoppingItems.clear();

        while (cursor.moveToNext()) {
            ShoppingItem item = new ShoppingItem(cursor.getString(keyId),
                    cursor.getString(keyNameIndex),
                    cursor.getString(keyquantIndex));
            shoppingItems.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
