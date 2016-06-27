package com.example.jonelezhang.cartopia;

/**
 * Created by Jonelezhang on 6/27/16.
 */
public class CarDetailsCommentsItem {
    private int id;
    private String content;
    private int user_id;

    public CarDetailsCommentsItem(){
    }

    //setting method
    public void setId(int id){
        this.id = id;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

    //getting method
    public int getId(){return id;}
    public String getContent(){return content;}
    public int getUser_id(){return user_id;}
}
