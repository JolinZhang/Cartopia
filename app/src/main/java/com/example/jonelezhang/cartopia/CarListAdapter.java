package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
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
//buy car list item adapter
public class CarListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<BuyCarItem> mCarItems;
    private static LayoutInflater inflater = null;

    public CarListAdapter(Context context, ArrayList<BuyCarItem> carItems){
        mContext = context;
        mCarItems = carItems;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = inflater.inflate(R.layout.buy_list_item,null);
        }else{
            view = convertView;
        }
        //get all widgets on car list card
        TextView buyPrice = (TextView) view.findViewById(R.id.buy_price);
        TextView buyMileage = (TextView) view.findViewById(R.id.buy_mileage);
        TextView buyYear = (TextView) view.findViewById(R.id.buy_year);
        TextView buyMake = (TextView) view.findViewById(R.id.buy_make);
        TextView buyModel = (TextView) view.findViewById(R.id.buy_model);
        TextView buyCity = (TextView) view.findViewById(R.id.buy_city);
        TextView buyState = (TextView) view.findViewById(R.id.buy_state);

        // initial bitmap as  null
        final ImageView buyImage = (ImageView)view.findViewById(R.id.buy_image);
        Bitmap output = null;
        buyImage.setImageBitmap(output);
        //get and set image in a separate thread
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    try {
                        //get image bitmap inputStream
                        String imageUrl = "http://cartopia.club/assets/user_car/"+mCarItems.get(position).getImageResourceId();
                        Bitmap src = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());

                        //rescale bitmap size. factor is 0.5
                        Matrix m = new Matrix();
                        int width = src.getWidth();
                        int height = src.getHeight();
                        int scaledWidth = (int) (width * 0.5);
                        int scaledHeight = (int) (height * 0.5);
                        m.setRectToRect(new RectF(0, 0, width , height), new RectF(0, 0, scaledWidth, scaledHeight), Matrix.ScaleToFit.CENTER);
                        Bitmap output = Bitmap.createBitmap(src, 0, 0, width, height, m, true);
                        // set image on image view buyImage
                        buyImage.setImageBitmap(output);
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


        //set all info on car list card
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
