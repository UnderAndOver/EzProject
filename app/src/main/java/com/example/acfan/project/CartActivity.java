package com.example.acfan.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private static final String TAG ="CartActivity";
    Cart cart;
    Intent intent;
    ListView l;
    CartItemAdapter adapter;
    TextView total;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);
        intent = getIntent();
        l = (ListView) findViewById(R.id.cart_item_list);
        final Cart cart = CartHelper.getCart();
        adapter = new CartItemAdapter(this, cart);
        l.setAdapter(adapter);
        total = (TextView) findViewById(R.id.cart_item_total);
        total.setText(String.format("$%s", cart.getTotal()));
        clear =(Button) findViewById(R.id.clear);


        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                ArrayList<Item> cartItems = cart.getItems();
                Item item = cartItems.get(position);
                Log.d(TAG, "Viewing product: " + item.getName());
                Intent intent = new Intent(CartActivity.this, ItemDetailActivity.class);
                intent.putExtra("Item", item);
                startActivity(intent);
            }
        });

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(CartActivity.this)
                        .setTitle(getResources().getString(R.string.delete_item))
                        .setMessage(getResources().getString(R.string.delete_item_message))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Item helper=cart.getItems().get(position);
                                cart.removeItem(helper);
                                adapter.remove(helper);
                                adapter.updateCartItems(cart);
                                adapter.notifyDataSetChanged();
                                total.setText(String.format("$%s", cart.getTotal()));
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();
                return false;
            }


        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.clear();
                adapter.clear();
                adapter.updateCartItems(cart);
                adapter.notifyDataSetChanged();
                total.setText(String.format("$%s", "0"));
                Toast.makeText(CartActivity.this,"amount in cart : "+cart.getItems().size(),Toast.LENGTH_LONG).show();
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

