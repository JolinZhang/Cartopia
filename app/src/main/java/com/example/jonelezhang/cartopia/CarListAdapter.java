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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
    public View getView(final int position, View convertView, ViewGroup parent){
        final View view;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.buy_list_item,null);
        }else{
            view = convertView;
        }

        TextView buyPrice = (TextView) view.findViewById(R.id.buy_price);
        TextView buyMileage = (TextView) view.findViewById(R.id.buy_mileage);
        TextView buyYear = (TextView) view.findViewById(R.id.buy_year);
        TextView buyMake = (TextView) view.findViewById(R.id.buy_make);
        TextView buyModel = (TextView) view.findViewById(R.id.buy_model);
        TextView buyCity = (TextView) view.findViewById(R.id.buy_city);
        TextView buyState = (TextView) view.findViewById(R.id.buy_state);

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    try {
                        String imageUrl = "http://cartopia.club/assets/user_car/"+mCarItems.get(position).getImageResourceId();
                        Bitmap image = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
                        ImageView buyImage = (ImageView)view.findViewById(R.id.buy_image);
                        buyImage.setImageBitmap(Bitmap.createScaledBitmap(image, 800, 660, false));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();


//        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.car1);
//        buyImage.setImageBitmap(Bitmap.createScaledBitmap(image, 800, 660, false));

        buyPrice.setText("$"+mCarItems.get(position).getPrice() + "");
        buyMileage.setText(mCarItems.get(position).getMileage()+"mi");
        buyYear.setText( mCarItems.get(position).getYear()+" ");
        buyMake.setText(mCarItems.get(position).getMake()+" ");
        buyModel.setText( mCarItems.get(position).getModel()+" ");
        buyCity.setText(mCarItems.get(position).getCity()+",");
        buyState.setText( mCarItems.get(position).getState());
        return view;

    }
}
