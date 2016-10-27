package com.example.acfan.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by acfan on 10/21/2016.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.layout_item_template,parent,false);
        }
        TextView title=(TextView)convertView.findViewById(R.id.item_template_title);
        TextView price=(TextView)convertView.findViewById(R.id.item_template_price);
        TextView description=(TextView)convertView.findViewById(R.id.item_template_description);
        ImageView picture=(ImageView)convertView.findViewById(R.id.item_template_image);
        title.setText(item.getName());
        price.setText("$"+item.getPrice());
        description.setText(item.getDescription());
        picture.setImageResource(item.getImage());
        return convertView;
    }

}
