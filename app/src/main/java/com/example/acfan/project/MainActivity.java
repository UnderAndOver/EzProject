package com.example.acfan.project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String DEFAULT="N/A";
    public String url_token_login= "http://192.168.2.107:5000/tokenlogin"; //check token login
    Item_Frag item_frag;
    boolean flag=false;
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
            fragmentTransaction.add(R.id.activity_main, item_frag, "Frag");
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
        else if(Token_Verify().isEmpty()|| Token_Verify()==null){
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
    }


    public void OpenCart(View view) {
        Intent callCart = new Intent(this,CartActivity.class);
        startActivity(callCart);
    }

    public String Token_Verify(){
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
        SharedPreferences sharedPreferences=getSharedPreferences("Token", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token",DEFAULT);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_token_login,null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        SharedPreferences sharedPreferences = getSharedPreferences("Token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", "");
                        editor.commit();
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(!flag) {
            queue.add(jsonObjectRequest);
            flag = true;
        }
        else
            flag=false;
        token=sharedPreferences.getString("token",DEFAULT);
        return token;
    }
}
