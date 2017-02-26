package com.example.acfan.project;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.acfan.project.MainActivity.DEFAULT;


/**
 * A simple {@link Fragment} subclass.
 */
public class Item_Frag extends android.app.ListFragment{
    public static final String ITEM_DETAIL = "com.example.acfan.project.Item Detail";
    public static final String CART_KEY ="com.example.acfan.project.Cart Key";
    private ArrayList<Item> list;
    private ItemAdapter itemAdapter;
    private Item Itemchosen;
    private int itemamountchosen;
    private Intent cartintent;
    private String url_items= "192.168.2.106:5000/items"; //get item_list
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Request_Item_List();
        /*list = new ArrayList<>();
        String[] names = {"Hamburger", "Shakshuka", "Falafel", "Shawarma"};
        int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4};
        String[] descriptions = {
                "A hamburger is a sandwich consisting of one or more cooked patties of ground meat, usually beef, placed inside a sliced bread roll or bun. Hamburgers may be cooked in a variety of ways, including pan-frying, barbecuing, and flame-broiling.",
                "Shakshouka or shakshuka is a dish of eggs poached in a sauce of tomatoes, chili peppers, and onions, often spiced with cumin.",
                "Falafel is a deep-fried ball or patty made from ground chickpeas, fava beans, or both. Falafel is a traditional Middle Eastern food, commonly served in a pita, which acts as a pocket",
                "Shawarma or Shawurma is an Arab and Israeli meat preparation, where lamb, chicken, turkey, beef, veal, buffalo meat, or mixed meats are placed on a spit, and may be grilled for as long as a day."};
        float[] prices = {20, 15, 13, 17};
        for (int i = 0; i < names.length; i++) {
            list.add(new Item(names[i], images[i], descriptions[i], prices[i]));
        }
        */
        itemAdapter = new ItemAdapter(getActivity(), list);
        setListAdapter(itemAdapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);
        launchItemDetailActivity(position);
    }

    private void launchItemDetailActivity(int position){
        Item item = (Item)getListAdapter().getItem(position);
        Intent intent=new Intent(getActivity(),ItemDetailActivity.class);
        intent.putExtra("Item",item);
        startActivity(intent);
    }

    public void Request_Item_List(){
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token",DEFAULT);
        JSONObject userInfo = new JSONObject();
        try {
            userInfo.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomJsonArrayRequest jsonArrayRequest = new CustomJsonArrayRequest(Request.Method.GET,url_items,userInfo,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        getItemList(response);
                    }

                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                     /*   SharedPreferences sharedPreferences = getSharedPreferences("Token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", "");
                        editor.commit();
                        */
                        Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }
    private void getItemList(JSONArray response){ //need to add imageloader for urls
        ArrayList items = new ArrayList<>();

        for(int i=0;i<response.length();i++){
            try {
                JSONObject jsonItem = response.getJSONObject(i);

                Item item = new Item(jsonItem.getString("name"),R.drawable.placeholder,jsonItem.getString("description"), Float.valueOf(String.valueOf(jsonItem.getDouble("price"))));
                items.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        list=items;
    }


}
