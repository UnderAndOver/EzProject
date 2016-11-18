package com.example.acfan.project;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by acfan on 10/20/2016.
 */

public class Cart {
    private HashMap<Item,Integer> Item_Quantity;
    private float price;

    public Cart(){
        this.Item_Quantity=new HashMap<>();
        this.price =0;
    }
    public void addItem(int quantity,Item item){
        if(!this.Item_Quantity.containsKey(item)){
            this.Item_Quantity.put(item,quantity);
        }
    }
    public void removeItem(Item item){
        if(this.Item_Quantity.containsKey(item))
            this.Item_Quantity.remove(item);
    }

    public void updateTotal(Item item,int quantity){
        if(this.Item_Quantity.containsKey(item))
            this.Item_Quantity.put(item,quantity);
    }

    public float getTotal(){
        float sum=0;
        if(!Item_Quantity.isEmpty())
        for(Item helper:Item_Quantity.keySet()){
            sum+=helper.getPrice()*Item_Quantity.get(helper);
        }
        price =sum;
        return price;
    }

    public int getQuantity(Item item){
        return Item_Quantity.get(item);
    }

    public ArrayList<Item> getItems(){
        ArrayList<Item> itemlist=new ArrayList<>();
        for(Item helper: Item_Quantity.keySet())
            itemlist.add(helper);
        return itemlist;
    }

    public void clear(){
        this.Item_Quantity.clear();
    }

}
