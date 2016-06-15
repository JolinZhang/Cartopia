package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonelezhang on 6/13/16.
 */
public class CarListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<BuyCarItem> mCarItems;

    public CarListAdapter(Context context, ArrayList<BuyCarItem> carItems){
        mContext = context;
        mCarItems = carItems;
    }
    @Override
    public int getCount(){
        return mCarItems.size();
    }
    @Override
    public Object getItem(int position){return mCarItems.get(position);}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.buy_list_item,null);
        }else{
            view = convertView;
        }
        ImageView buyImage = (ImageView)view.findViewById(R.id.buy_image);
        TextView buyPrice = (TextView) view.findViewById(R.id.buy_price);
        TextView buyMileage = (TextView) view.findViewById(R.id.buy_mileage);
        TextView buyYear = (TextView) view.findViewById(R.id.buy_year);
        TextView buyMake = (TextView) view.findViewById(R.id.buy_make);
        TextView buyModel = (TextView) view.findViewById(R.id.buy_model);
        TextView buyCity = (TextView) view.findViewById(R.id.buy_city);
        TextView buyState = (TextView) view.findViewById(R.id.buy_state);




        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.car1);
        buyImage.setImageBitmap(Bitmap.createScaledBitmap(image, 900, 706, false));
        buyPrice.setText(mCarItems.get(position).getPrice() + "");
        buyMileage.setText(mCarItems.get(position).getMileage()+"");
        buyYear.setText( mCarItems.get(position).getYear()+"");
        buyMake.setText(mCarItems.get(position).getMake());
        buyModel.setText( mCarItems.get(position).getModel());
        buyCity.setText(mCarItems.get(position).getCity());
        buyState.setText( mCarItems.get(position).getState());
        return view;

    }
}
