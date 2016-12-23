package com.example.acfan.project;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acfan on 10/13/2016.
 */

public class Item implements Parcelable {
    private String name,description;
    private int id,rating,image;
    private float price;

    public Item(String name, int image, String description, float price){
        this.name=name;
        this.image=image;
        this.rating=0;
        this.description=description;
        this.id=hashCode();
        this.price=price;
    }

    public Item(Parcel input){
        name=input.readString();
        description=input.readString();
        id=input.readInt();
        rating=input.readInt();
        image=input.readInt();
        price=input.readFloat();
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public int getId(){
        return id;
    }
    public int getImage(){
        return image;
    }
    public int getRating(){
        return rating;
    }
    public float getPrice(){
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return name.equals(item.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(id);
        dest.writeInt(rating);
        dest.writeInt(image);
        dest.writeFloat(price);
    }

    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>(){
        public Item createFromParcel(Parcel in){
            return new Item(in);
        }
        public Item[] newArray(int size){
            return new Item[size];
        }
    };
}