package com.example.acfan.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by acfan on 10/20/2016.
 */

public class Cart implements Parcelable {
    private HashMap<Item, Integer> Item_Quantity;
    private float price;

    public Cart() {
        this.Item_Quantity = new HashMap<>();
        this.price = 0;
    }

    public Cart(Parcel input) {
        int size = input.readInt();
        Item_Quantity = new HashMap<>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Item key = input.readParcelable(Item.class.getClassLoader());
                int value = input.readInt();
                Item_Quantity.put(key, value);
            }
            price=getTotal();
        }
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public void addItem(int quantity, Item item) {
        if (!this.Item_Quantity.containsKey(item)) {
            this.Item_Quantity.put(item, quantity);
        }
        else{
            updateTotal(item,quantity);
        }
    }

    public void removeItem(Item item) {
        if (this.Item_Quantity.containsKey(item))
            this.Item_Quantity.remove(item);
    }

    public void updateTotal(Item item, int quantity) {
        if (this.Item_Quantity.containsKey(item))
            this.Item_Quantity.put(item, quantity);
    }

    public float getTotal() {
        float sum = 0;
        if (!Item_Quantity.isEmpty())
            for (Item helper : Item_Quantity.keySet()) {
                sum += helper.getPrice() * Item_Quantity.get(helper);
            }
        price = sum;
        return price;
    }

    public int getQuantity(Item item) {
        if (this.Item_Quantity.containsKey(item))
        return Item_Quantity.get(item);
        return 0;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> itemlist = new ArrayList<>();
        for (Item helper : Item_Quantity.keySet())
            itemlist.add(helper);
        return itemlist;
    }

    public void clear() {
        this.Item_Quantity.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Item_Quantity.size());
        for (Map.Entry<Item, Integer> entry : Item_Quantity.entrySet()) {
            dest.writeParcelable(entry.getKey(), flags);
            dest.writeInt(entry.getValue());
        }
    }
}