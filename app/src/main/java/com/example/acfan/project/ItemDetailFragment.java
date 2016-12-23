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
    public static final String ITEM_CART ="com.example.acfan.project.Item Cart";
    private int count=1;
    TextView amount;
    public static final String ITEM_AMOUNT_EXTRA="com.example.acfan.project.Item Amount";
    Intent intent;
    Item item;
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
        intent=getActivity().getIntent();
        item = (intent.getExtras().getParcelable(Item_Frag.ITEM_DETAIL));
        name.setText(item.getName());
        description.setText(item.getDescription());
        price.setText(new StringBuilder().append("$").append(item.getPrice()).toString());
        picture.setImageResource(item.getImage());
        return fragmentLayout;
    }

    public void count(View v) {
        count=Integer.parseInt(amount.getText().toString());
        switch (v.getId()) {
            case (R.id.plus):
                if(count<20)
                count++;
                break;
            case (R.id.minus):
                if(count>1)
                count--;
                break;
            default:
                break;
        }
        amount.setText(""+count);
    }

    public void addtocart(View v){
        Intent cartintent= new Intent(getActivity(),MainActivity.class);
        cartintent.putExtra(ITEM_AMOUNT_EXTRA,count);
        cartintent.putExtra(ITEM_CART,item);
        startActivity(cartintent);

    }

}
