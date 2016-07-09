package com.example.jonelezhang.cartopia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jonelezhang on 6/26/16.
 */
public class GetImage {
    public static void getImage(final ImageView buyImage, final String ImageRescources) {
        Bitmap output = null;
        buyImage.setImageBitmap(output);
        //get and set image in a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        //get image bitmap inputStream
                        String imageUrl = "http://cartopia.club/assets/user_car/" + ImageRescources;
                        Bitmap src = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
                        //rescale bitmap size. factor is 0.5
                        Matrix m = new Matrix();
                        int width = src.getWidth();
                        int height = src.getHeight();
                        int scaledWidth = (int) (width * 0.5);
                        int scaledHeight = (int) (height * 0.5);
                        m.setRectToRect(new RectF(0, 0, width, height), new RectF(0, 0, scaledWidth, scaledHeight), Matrix.ScaleToFit.CENTER);
                        final Bitmap output = Bitmap.createBitmap(src, 0, 0, width, height, m, true);
                        // set image on image view buyImage
                        buyImage.post(new Runnable() {
                            @Override
                            public void run() {
                                buyImage.setImageBitmap(output);
                            }
                        });

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
    }

}
