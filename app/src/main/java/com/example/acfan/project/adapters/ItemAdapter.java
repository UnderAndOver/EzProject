package com.example.acfan.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.acfan.project.R;
import com.example.acfan.project.network.VolleySingleton;
import com.example.acfan.project.models.Item;

import java.util.ArrayList;

/**
 * Created by acfan on 10/21/2016.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_template, parent, false);
        }
        if (mImageLoader == null)
            mImageLoader = VolleySingleton.getInstance().getImageLoader();
            TextView title = (TextView) convertView.findViewById(R.id.item_template_title);
            TextView price = (TextView) convertView.findViewById(R.id.item_template_price);
            TextView description = (TextView) convertView.findViewById(R.id.item_template_description);
            NetworkImageView mNetworkImageView = (NetworkImageView) convertView.findViewById(R.id.item_template_image);
            mNetworkImageView.setImageUrl(item.getImageurl(), mImageLoader);
            title.setText(item.getName());
            price.setText("$" + item.getPrice());
            description.setText(item.getDescription());
            //getting image
            //mImageLoader.get(item.getImageurl(), mImageLoader.getImageListener(mNetworkImageView, R.drawable.android_placeholder_vector, R.drawable.android_error_vector));
        return convertView;
    }

}
