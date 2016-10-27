package com.example.acfan.project;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by acfan on 10/22/2016.
 */

public class ItemDetailFragment extends Fragment {
    private int count;
    TextView amount;
    public ItemDetailFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View fragmentLayout=inflater.inflate(R.layout.fragment_item_detailed,container,false);
        TextView name=(TextView) fragmentLayout.findViewById(R.id.Item_Name);
        TextView description=(TextView)fragmentLayout.findViewById(R.id.Item_description);
        TextView price =(TextView)fragmentLayout.findViewById(R.id.Item_Price);
        ImageView picture=(ImageView)fragmentLayout.findViewById(R.id.Item_Image);
        amount = (TextView)fragmentLayout.findViewById(R.id.counter);
        Intent intent=getActivity().getIntent();
        name.setText(intent.getExtras().getString(MainActivity.ITEM_NAME_EXTRA));
        description.setText(intent.getExtras().getString(MainActivity.ITEM_DESCRIPTION_EXTRA));
        price.setText(intent.getExtras().getString(MainActivity.ITEM_PRICE_EXTRA));
        picture.setImageResource(intent.getExtras().getInt(MainActivity.ITEM_PICTURE_EXTRA));
        return fragmentLayout;
    }

    public void count(View v) {
        count=Integer.parseInt(amount.getText().toString());
        switch (v.getId()) {
            case (R.id.plus):
                if(count<5)
                count++;
                break;
            case (R.id.minus):
                if(count>0)
                count--;
                break;
        }
        amount.setText(""+count);
    }

}
