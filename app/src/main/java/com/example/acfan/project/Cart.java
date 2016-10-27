package com.example.acfan.project;

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
        if(this.Item_Quantity.containsKey(item)){
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

    public void getTotal(){
        float sum=0;
        if(!Item_Quantity.isEmpty())
        for(Item helper:Item_Quantity.keySet()){
            sum+=helper.getPrice()*Item_Quantity.get(helper);
        }
        price =sum;
    }

    public void clear(){
        this.Item_Quantity.clear();
    }

}
