package com.example.acfan.project;

/**
 * Created by acfan on 10/13/2016.
 */

public class Item {
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
}
