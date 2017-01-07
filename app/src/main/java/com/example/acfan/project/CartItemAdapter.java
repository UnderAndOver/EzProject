package com.example.acfan.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by acfan on 11/17/2016.
 */

public class CartItemAdapter extends ArrayAdapter<Item> {
    Cart helper;
    public CartItemAdapter(Context context,Cart cart) {
        super(context, 0, cart.getItems());
        helper=cart;
    }

    public void updateCartItems(Cart cart){
        this.helper=cart;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.layout_cart_item,parent,false);
        TextView itemname=(TextView) convertView.findViewById(R.id.item_cart_name);
        TextView itemprice = (TextView) convertView.findViewById(R.id.item_cart_price);
        TextView itemquantity = (TextView) convertView.findViewById(R.id.item_cart_quantity);
        ImageView itempicutre = (ImageView) convertView.findViewById(R.id.item_cart_picture);
        itemname.setText(item.getName());
        itemprice.setText(String.format("$%s", item.getPrice()));
        itempicutre.setImageResource(item.getImage());
        itemquantity.setText(""+helper.getQuantity(item));
        return convertView;
    }
}
