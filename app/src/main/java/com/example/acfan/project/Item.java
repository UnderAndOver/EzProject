package com.example.acfan.project;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acfan on 10/13/2016.
 */

public class Item implements Parcelable {
    private String name,description,imageurl,id;
    private int rating;
    private float price;

    public Item(String name, String imageurl, String description, float price,String id){
        this.name=name;
        this.imageurl=imageurl;
        this.rating=0;
        this.description=description;
        this.id=id;
        this.price=price;
    }

    public Item(Parcel input){
        name=input.readString();
        description=input.readString();
        id=input.readString();
        rating=input.readInt();
        price=input.readFloat();
        imageurl=input.readString();
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getImageurl(){return imageurl;}
    public String getId(){
        return id;
    }
    public String getImage(){
        return imageurl;
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
        dest.writeString(id);
        dest.writeInt(rating);
        dest.writeFloat(price);
        dest.writeString(imageurl);
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