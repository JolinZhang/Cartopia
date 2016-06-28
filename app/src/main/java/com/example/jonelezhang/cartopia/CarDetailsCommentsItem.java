package com.example.jonelezhang.cartopia;

/**
 * Created by Jonelezhang on 6/27/16.
 */
public class CarDetailsCommentsItem {
    private int id;
    private String content;
    private int user_id;
    private String user_name;

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
    public void setUser_name(String user_name){this.user_name = user_name;}

    //getting method
    public int getId(){return id;}
    public String getContent(){return content;}
    public int getUser_id(){return user_id;}
    public String getUser_name(){return user_name;}
}
