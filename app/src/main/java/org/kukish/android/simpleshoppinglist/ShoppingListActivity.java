package org.kukish.android.simpleshoppinglist;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.kukish.android.simpleshoppinglist.adapters.ShoppingItemAdapter;
import org.kukish.android.simpleshoppinglist.fragments.ShoppingListFragment;
import org.kukish.android.simpleshoppinglist.listeners.OnItemDeletedListener;
import org.kukish.android.simpleshoppinglist.listeners.OnNewItemAddedListener;

import java.util.ArrayList;

public class ShoppingListActivity extends Activity
        implements OnNewItemAddedListener, OnItemDeletedListener {

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

    }

    @Override
    public void onNewItemAdded(String itemName, String quantity) {
        ShoppingItem newItem = new ShoppingItem(itemName, quantity);
        shoppingItems.add(0, newItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(int position) {
        shoppingItems.remove(position);
        adapter.notifyDataSetChanged();
    }
}
