package com.example.acfan.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private static final String TAG ="CartActivity";
    Cart cart;
    Intent intent;
    ListView l;
    CartItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);
        intent = getIntent();
        l = (ListView) findViewById(R.id.cart_item_list);
        final Cart cart = CartHelper.getCart();
        adapter = new CartItemAdapter(this, cart);
        l.setAdapter(adapter);
        TextView total = (TextView) findViewById(R.id.cart_item_total);
        total.setText(String.format("$%s", cart.getTotal()));


        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                ArrayList<Item> cartItems = cart.getItems();
                Item item = cartItems.get(position);
                Log.d(TAG, "Viewing product: " + item.getName());
                Intent intent = new Intent(CartActivity.this, ItemDetailActivity.class);
                intent.putExtra("Item",item);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}

