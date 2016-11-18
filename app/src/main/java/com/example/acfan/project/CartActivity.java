package com.example.acfan.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {
    Cart cart;
    Intent intent;
    ListView l;
    CartItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);
        intent = getIntent();
        l=(ListView)findViewById(R.id.cart_item_list);
        cart=new Cart();
        additem();
        adapter=new CartItemAdapter(this,cart);
        l.setAdapter(adapter);
        TextView total = (TextView)findViewById(R.id.cart_item_total);
        total.setText(String.format("$%s", cart.getTotal()));
    }


    private void additem() {
        String itemname= intent.getExtras().getString(MainActivity.ITEM_NAME_EXTRA);
        String itempricestring= (intent.getExtras().getString(MainActivity.ITEM_PRICE_EXTRA));
        float itemprice = Float.parseFloat(itempricestring.substring(1,itempricestring.length()-1));
        int itempicture= intent.getExtras().getInt(MainActivity.ITEM_PICTURE_EXTRA);
        int itemamount= (intent.getExtras().getInt(ItemDetailFragment.ITEM_AMOUNT_EXTRA));
        String itemdescription = intent.getExtras().getString(MainActivity.ITEM_DESCRIPTION_EXTRA);
        Item item = new Item(itemname,itempicture,itemdescription,itemprice);
        cart.addItem(itemamount,item);
    }
}
