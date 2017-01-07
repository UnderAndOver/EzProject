package com.example.acfan.project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Item_Frag item_frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item_frag=new Item_Frag();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main,item_frag,"Frag");
        fragmentTransaction.commit();
    }

    public void OpenCart(View view) {
        Intent callCart = new Intent(this,CartActivity.class);
        startActivity(callCart);
    }
}
