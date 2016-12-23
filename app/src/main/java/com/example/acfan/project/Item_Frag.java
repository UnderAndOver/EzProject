package com.example.acfan.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Item_Frag extends android.app.ListFragment{
    public static final String ITEM_DETAIL = "com.example.acfan.project.Item Detail";
    public static final String CART_KEY ="com.example.acfan.project.Cart Key";
    private ArrayList<Item> list;
    private ItemAdapter itemAdapter;
    private Item Itemchosen;
    private int itemamountchosen;
    private Intent cartintent;
    public Cart cart;

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<>();
        String[] names = {"Hamburger", "Shakshuka", "Falafel", "Shawarma"};
        int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4};
        String[] descriptions = {
                "A hamburger is a sandwich consisting of one or more cooked patties of ground meat, usually beef, placed inside a sliced bread roll or bun. Hamburgers may be cooked in a variety of ways, including pan-frying, barbecuing, and flame-broiling.",
                "Shakshouka or shakshuka is a dish of eggs poached in a sauce of tomatoes, chili peppers, and onions, often spiced with cumin.",
                "Falafel is a deep-fried ball or patty made from ground chickpeas, fava beans, or both. Falafel is a traditional Middle Eastern food, commonly served in a pita, which acts as a pocket",
                "Shawarma or Shawurma is an Arab and Israeli meat preparation, where lamb, chicken, turkey, beef, veal, buffalo meat, or mixed meats are placed on a spit, and may be grilled for as long as a day."};
        float[] prices = {20, 15, 13, 17};
        for (int i = 0; i < 4; i++) {
            list.add(new Item(names[i], images[i], descriptions[i], prices[i]));
        }
        itemAdapter = new ItemAdapter(getActivity(), list);
        setListAdapter(itemAdapter);
        if(savedInstanceState!=null)
            cart=savedInstanceState.getParcelable(CART_KEY);
        if (getActivity().getIntent().getExtras() != null) {
            cartintent = getActivity().getIntent();
            Itemchosen = (Item) cartintent.getExtras().get(ItemDetailFragment.ITEM_CART);
            itemamountchosen = (Integer) cartintent.getExtras().get(ItemDetailFragment.ITEM_AMOUNT_EXTRA);
            cart.addItem(itemamountchosen,Itemchosen);
        }
        else{
            cart=new Cart();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CART_KEY,cart);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);
        launchItemDetailActivity(position);
    }

    private void launchItemDetailActivity(int position){
        Item item = (Item)getListAdapter().getItem(position);
        Intent intent=new Intent(getActivity(),ItemDetailActivity.class);
        intent.putExtra(ITEM_DETAIL,item);
        startActivity(intent);
    }
}
