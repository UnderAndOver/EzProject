package com.example.acfan.project.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.acfan.project.R;
import com.example.acfan.project.network.VolleySingleton;
import com.example.acfan.project.fragments.Item_Frag;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String DEFAULT="N/A";
    public String url_token_login= "http://192.168.2.107:5000/tokenlogin"; //check token login
    Item_Frag item_frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if not signed in, close activity and launch login screen
        if(!isUserSignedIn()){
            finish();
            startLoginActivity();
        }
        else {
            setContentView(R.layout.activity_main);
            item_frag = new Item_Frag();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.list, item_frag, "Frag");
            fragmentTransaction.commit();
        }
    }

    private boolean isUserSignedIn() {
        SharedPreferences sharedPreferences=getSharedPreferences("Token", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","DEFAULT"); // checking if token
        if(token.isEmpty()){ // NEED TO CHECK IF TOKEN IS VALID
            // user is new implement splash screen here
            return false;
        }
        else if(Token_Verify(token).isEmpty()){
            Toast.makeText(getApplicationContext(),"Sorry something went wrong, you will need to login again",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }


    public void OpenCart(View view) {
        Intent callCart = new Intent(this,CartActivity.class);
        startActivity(callCart);
        finish();
    }

    public String Token_Verify(String user_token){
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        String token = user_token;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_token_login,null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("response");
                            if(code.equals("EXPIRED")){
                                SharedPreferences sharedPreferences = getSharedPreferences("Token", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", "");
                                editor.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Response", error.toString());
                        String err;
                        // gets here if server and internet are on
                        NetworkResponse networkResponse=error.networkResponse;
                        if (networkResponse != null && networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            startLoginActivity();
                            Toast.makeText(getApplicationContext(), "Sorry, but you will have to login again", Toast.LENGTH_LONG).show();
                        }
                        if (error instanceof com.android.volley.NoConnectionError) {
                            err = "No Internet Access";
                            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
                        }
                        else if (error.getClass().equals(TimeoutError.class)){
                            err="Server is unavailable";
                            Toast.makeText(getApplicationContext(),err,Toast.LENGTH_SHORT).show();
                        }
                        else {
                        SharedPreferences sharedPreferences = getSharedPreferences("Token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", "");
                        editor.apply();
                        err = "Something went wrong";
                        Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
                        }
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
        return token;
    }
}
