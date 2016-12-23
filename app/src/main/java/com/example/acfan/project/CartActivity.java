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
    }
}
