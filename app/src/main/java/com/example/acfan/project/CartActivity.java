package com.example.acfan.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.acfan.project.MainActivity.DEFAULT;

public class CartActivity extends AppCompatActivity {
    private static final String TAG ="CartActivity";
    private String url_order="http://192.168.2.107:5000/orders"; //link to get/post orders
    Cart cart;
    Intent intent;
    ListView l;
    CartItemAdapter adapter;
    TextView total;
    Button clear,buy;

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
        buy = (Button) findViewById(R.id.send_order);


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
                new AlertDialog.Builder(CartActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
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
                return true;
            }


        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.clear();
                adapter.clear();
                adapter.updateCartItems(cart);
                adapter.notifyDataSetChanged();
                total.setText(String.format("$%s", "0.0"));
                Toast.makeText(CartActivity.this,"amount in cart : "+cart.getItems().size(),Toast.LENGTH_LONG).show();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(!cart.getItems().isEmpty()) {
                    ArrayList<Item> items = cart.getItems();
                    JSONArray jsonitems = new JSONArray();
                    JSONObject order = new JSONObject();
                    for (Item helper : items) {
                        String id = helper.getId();
                        int amount = cart.getQuantity(helper);
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("item_id", id);
                            obj.put("amount", amount);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonitems.put(obj);
                    }
                    try {
                        order.put("items", jsonitems);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cart.clear();
                    adapter.clear();
                    adapter.updateCartItems(cart);
                    adapter.notifyDataSetChanged();
                    total.setText(String.format("$%s", "0.0"));
                    sendordertoserver(order);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Buy something first",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendordertoserver(JSONObject jsonitems) {
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_order, jsonitems,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"Thank you for using EZ-Meal",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                     /*   SharedPreferences sharedPreferences = getSharedPreferences("Token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", "");
                        editor.commit();
                        */
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("Token", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", DEFAULT);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}

