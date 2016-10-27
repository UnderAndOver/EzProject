package com.example.acfan.project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String ITEM_ID_EXTRA="com.example.acfan.project.Item Id";
    public static final String ITEM_NAME_EXTRA="com.example.acfan.project.Item Name";
    public static final String ITEM_PRICE_EXTRA="com.example.acfan.project.Item Price";
    public static final String ITEM_PICTURE_EXTRA="com.example.acfan.project.Item Picture";
    public static final String ITEM_DESCRIPTION_EXTRA="com.example.acfan.project.Item Description";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Item_Frag item_frag=new Item_Frag();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main,item_frag,"Frag");
        fragmentTransaction.commit();
    }
}
