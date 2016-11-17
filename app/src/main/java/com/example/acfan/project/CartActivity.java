package com.example.acfan.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Cart cart;
    Intent intent;
    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);
        intent = getIntent();
        l=(ListView)findViewById(R.id.cart_item_list);
        additem();
        showcart();
    }

    private void showcart() {
        ArrayList<Item> itemlist = cart.getItems();

    }

    private void additem() {
        String itemname= intent.getExtras().getString(MainActivity.ITEM_NAME_EXTRA);
        float itemprice= Float.parseFloat(intent.getExtras().getString(MainActivity.ITEM_PRICE_EXTRA));
        int itempicture= intent.getExtras().getInt(MainActivity.ITEM_PICTURE_EXTRA);
        int itemamount= Integer.parseInt(intent.getExtras().getString(ItemDetailFragment.ITEM_AMOUNT_EXTRA));
        String itemdescription = intent.getExtras().getString(MainActivity.ITEM_DESCRIPTION_EXTRA);
        Item item = new Item(itemname,itempicture,itemdescription,itemprice);
        cart.addItem(itemamount,item);
    }
}
