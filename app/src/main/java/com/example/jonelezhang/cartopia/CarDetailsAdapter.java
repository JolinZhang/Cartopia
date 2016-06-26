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
 * Created by Jonelezhang on 6/26/16.
 */
public class CarDetailsAdapter extends BaseAdapter {
    Context mContext;
    BuyCarItem mBuyCarItem;
    public CarDetailsAdapter(Context context, BuyCarItem buyCarItem) {
        mContext = context;
        mBuyCarItem = buyCarItem;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        view = convertView;

        //get all widgets on car list card
        TextView carDetailsPrice = (TextView) view.findViewById(R.id.carDetails_price);
        TextView carDetailsMileage = (TextView) view.findViewById(R.id.carDetails_mileage);
        TextView carDetailsYear = (TextView) view.findViewById(R.id.carDetails_year);
        TextView carDetailsMake = (TextView) view.findViewById(R.id.carDetails_make);
        TextView carDetailsModel = (TextView) view.findViewById(R.id.carDetails_model);
        TextView carDetailsCity = (TextView) view.findViewById(R.id.carDetails_city);
        TextView carDetailsState = (TextView) view.findViewById(R.id.carDetails_state);
        TextView carDetailsContact = (TextView) view.findViewById(R.id.carDetails_contact);
        TextView carDetailsNotes = (TextView) view.findViewById(R.id.carDetails_note);
        TextView carDetailsCreatedAt = (TextView) view.findViewById(R.id.carDetails_createAt);

        // initial bitmap as  null
        final ImageView carDetailsImage = (ImageView)view.findViewById(R.id.carDetails_image);
        Bitmap output = null;
        carDetailsImage.setImageBitmap(output);
        //get and set image in a separate thread
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    try {
                        //get image bitmap inputStream
                        String imageUrl = "http://cartopia.club/assets/user_car/"+mBuyCarItem.getImageResourceId();
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
                        carDetailsImage.setImageBitmap(output);
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
        carDetailsPrice.setText("$"+mBuyCarItem.getPrice() + "");
        carDetailsMileage.setText(mBuyCarItem.getMileage() + "mi");
        carDetailsYear.setText( mBuyCarItem.getYear()+" ");
        carDetailsMake.setText(mBuyCarItem.getMake()+" ");
        carDetailsModel.setText( mBuyCarItem.getModel()+" ");
        carDetailsCity.setText(mBuyCarItem.getCity()+",");
        carDetailsState.setText( mBuyCarItem.getState());
        carDetailsContact.setText(mBuyCarItem.getContact());
        carDetailsNotes.setText( mBuyCarItem.getNotes());
        carDetailsCreatedAt.setText(mBuyCarItem.getCreatedAt());
        return view;
    }
}
