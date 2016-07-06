package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonelezhang on 6/27/16.
 */
public class CarDetailsCommentsAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CarDetailsCommentsItem> mCommentItems;
    private static LayoutInflater inflater = null;

    public CarDetailsCommentsAdapter(Context context, ArrayList<CarDetailsCommentsItem> carItems){
        mContext = context;
        mCommentItems = carItems;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mCommentItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if(convertView == null){
            view = inflater.inflate(R.layout.comment_list_item,null);
        }else{
            view = convertView;
        }
        //get all widgets on car list card
        TextView user = (TextView) view.findViewById(R.id.comment_user);
        TextView content = (TextView) view.findViewById(R.id.comment_content);

        //set all info on car list card
        user.setText(mCommentItems.get(position).getUser_name() + "");
        content.setText(mCommentItems.get(position).getContent() + "");

        //get user id
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_name = sharedpreferences.getString("Current_User_Name", "");
        //get delete button
        AppCompatButton delete = (AppCompatButton) view.findViewById(R.id.comment_delete);
        if(user_name.equals(mCommentItems.get(position).getUser_name() + "")){
            //set click issue of add comment
            delete.setVisibility(View.VISIBLE);
        }else{
            delete.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
