package com.example.jonelezhang.cartopia;

/**
 * Created by Jonelezhang on 6/13/16.
 */
public class BuyCarItem {
    private int id;
//    private String imageResourcesId;
    private int  price;
    private int mileage;
    private int year;
    private String make;
    private String model;
    private String city;
    private String state;
//    private String contact;
//    private String  notes;
//    private boolean issold;

    public BuyCarItem(){
    }
    //set methods
    public void setId(Integer id){
        this.id = id;
    }
//    public void setImageResourceId(String imageResourceId){
//        this.imageResourcesId = imageResourceId;
//    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setMileage(int mileage){
        this.mileage = mileage;
    }
    public void setYear(int year){
        this.year = year;
    }
    public void setMake(String make){
        this.make = make;
    }
    public void setModel(String model){
        this.model = model;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setState(String state){
        this.state = state;
    }
    // get methods
    public int   getId(){return id;}
//    public String getImageResourceId(){
//        return imageResourcesId;
//    }
    public int getPrice(){return price;}
    public int getMileage(){return mileage;}
    public int getYear(){return year;}
    public String getMake(){return make;}
    public String getModel(){return model;}
    public String getCity(){return city;}
    public String getState(){return state;}
}

