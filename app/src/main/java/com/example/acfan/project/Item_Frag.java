package com.example.acfan.project;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.acfan.project.MainActivity.DEFAULT;


/**
 * A simple {@link Fragment} subclass.
 */
public class Item_Frag extends android.app.ListFragment{
    public static final String ITEM_DETAIL = "com.example.acfan.project.Item Detail";
    public static final String CART_KEY ="com.example.acfan.project.Cart Key";
    private ArrayList<Item> list;
    private ItemAdapter itemAdapter;
    private ProgressDialog pDialog;
    private Intent cartintent;
    private String url_items= "http://192.168.2.107:5000/items"; //get item_list
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list=new ArrayList<>();
        itemAdapter = new ItemAdapter(getActivity(), list);
        setListAdapter(itemAdapter);

        //setup itemlist from server
        getItems();
        //Toast.makeText(getActivity(),"List size check: "+list.size(),Toast.LENGTH_SHORT).show();
    }
    private void getItems() {
            //Progress bar while getting items
            pDialog = new ProgressDialog(this.getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Getting Items....");
            pDialog.show();

            RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", DEFAULT);
            CustomJsonArrayRequest jsonArrayRequest = new CustomJsonArrayRequest(Request.Method.GET, url_items, null,

                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("onResponse", response.toString());
                            hidePDialog();
                            // json parse
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    String name = obj.getString("name");
                                    String description = obj.getString("description");
                                    String imageurl = obj.getString("image");
                                    Float price = (float) obj.getDouble("price");
                                    String id = obj.getString("item_id");
                                    Item item = new Item(name, imageurl, description, price,id);
                                    list.add(item);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            itemAdapter.notifyDataSetChanged();
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
                            VolleyLog.d("onErrorResponse", "Error: " + error.getMessage());
                            hidePDialog();
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", DEFAULT);
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };
           jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonArrayRequest);
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(pDialog!=null)
                    pDialog.dismiss();
                }
            }, 2000);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog(){
        if(pDialog!=null){
            pDialog.dismiss();
            pDialog=null;
        }
    }
    

}
