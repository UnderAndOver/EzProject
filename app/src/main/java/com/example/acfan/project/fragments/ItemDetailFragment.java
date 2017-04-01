package com.example.acfan.project.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.acfan.project.R;
import com.example.acfan.project.network.VolleySingleton;
import com.example.acfan.project.activities.CartActivity;
import com.example.acfan.project.models.Cart;
import com.example.acfan.project.models.Item;
import com.example.acfan.project.utils.CartHelper;

/**
 * Created by acfan on 10/22/2016.
 */

public class ItemDetailFragment extends Fragment {
    public static final String ITEM_CART ="com.example.acfan.project.models.Item Cart";
    private static final String TAG ="ItemDetailFragment";
    private int count=1;
    public static final String ITEM_AMOUNT_EXTRA="com.example.acfan.project.models.Item Amount";
    Intent intent;
    Item item;
    TextView name,description,price,amount;
    NetworkImageView mNetworkImageView;
    View fragmentLayout;
    Button bOrder;
    public ItemDetailFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        fragmentLayout=inflater.inflate(R.layout.fragment_item_detailed,container,false);
        intent=getActivity().getIntent();
        Bundle data=intent.getExtras();
        item = (Item) data.getParcelable("Item");

        retrieveViews();

        setItemProperties();
        addtocart();

        return fragmentLayout;
    }

    private void retrieveViews(){
        name=(TextView) fragmentLayout.findViewById(R.id.Item_Name);
        description=(TextView)fragmentLayout.findViewById(R.id.Item_description);
        price =(TextView)fragmentLayout.findViewById(R.id.Item_Price);
        mNetworkImageView=(NetworkImageView)fragmentLayout.findViewById(R.id.Item_Image);
        amount = (TextView)fragmentLayout.findViewById(R.id.counter);
        bOrder = (Button)fragmentLayout.findViewById(R.id.bOrder);
    }

    private void setItemProperties(){
        name.setText(item.getName());
        description.setText(item.getDescription());
        price.setText(new StringBuilder().append("$").append(item.getPrice()).toString());

        ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
        mImageLoader.get(item.getImageurl(), ImageLoader.getImageListener(mNetworkImageView,R.drawable.android_placeholder_vector,R.drawable.android_error_vector));
        mNetworkImageView.setImageUrl(item.getImageurl(),mImageLoader);

        if(CartHelper.getCart().getItems().contains(item))
        count = CartHelper.getCart().getQuantity(item);
        else
            count=1;
        amount.setText(""+count);
    }

    public void count(View v) {
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

    public void addtocart(){
        bOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = CartHelper.getCart();
                Log.d(TAG, "Adding product: " + item.getName());
                cart.addItem(count,item);
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }


}
