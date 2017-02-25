package com.example.acfan.project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String DEFAULT="N/A";
    Item_Frag item_frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if not signed in, close activity and launch login screen
        if(!isUserSignedIn()){
            finish();
            startLoginActivity();
            return;
        }

        setContentView(R.layout.activity_main);
        item_frag=new Item_Frag();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main,item_frag,"Frag");
        fragmentTransaction.commit();
    }

    private boolean isUserSignedIn() {
        SharedPreferences sharedPreferences=getSharedPreferences("Token", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","DEFAULT"); // checking if token
        if(token.equals(DEFAULT)){ // NEED TO CHECK IF TOKEN IS VALID
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
}
