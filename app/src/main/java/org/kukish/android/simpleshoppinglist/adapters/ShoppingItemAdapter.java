package org.kukish.android.simpleshoppinglist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.kukish.android.simpleshoppinglist.R;
import org.kukish.android.simpleshoppinglist.ShoppingItem;

import java.util.List;

/**
 * Created by Alejandro Awesome on 9/27/2016.
 */

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {
    private int resource;

    public ShoppingItemAdapter(Context context, int resource, List<ShoppingItem> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LinearLayout itemView;
        ShoppingItem item = getItem(position);

        assert item != null;
        String itemName = item.getName();
        String quanity = item.getQuantity();
        String itemDescription = item.getDescription();

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView itemNameView = (TextView) itemView.findViewById(R.id.listItemId);
        TextView itemqQuantView = (TextView) itemView.findViewById(R.id.listItemQuantity);

        itemNameView.setText(itemName);
        itemqQuantView.setText(quanity);

        return itemView;
    }
}
