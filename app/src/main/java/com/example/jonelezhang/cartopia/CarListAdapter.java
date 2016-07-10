package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
    //favorite animation
    private String addFav;
    private String delFav;
    static JSONObject del_fav_obj = null;
    private String _car_id;
    private int count = 1;
//    private ImageView favorite;
    //JSON Node Names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ID = "id";

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
        ImageView favorite = (ImageView) view.findViewById(R.id.favorite);

        // initial bitmap as  null
        final ImageView buyImage = (ImageView)view.findViewById(R.id.buy_image);
//        GetImage.getImage(buyImage,mCarItems.get(position).getImageResourceId());
        Glide
                .with(mContext)
                .load("http://cartopia.club/assets/user_car/" + mCarItems.get(position).getImageResourceId())
                .crossFade()
                .into(buyImage);


        //set all info on car list card
        buyPrice.setText("$"+mCarItems.get(position).getPrice() + "");
        buyMileage.setText(mCarItems.get(position).getMileage() + "mi");
        buyYear.setText( mCarItems.get(position).getYear()+" ");
        buyMake.setText(mCarItems.get(position).getMake()+" ");
        buyModel.setText( mCarItems.get(position).getModel()+" ");
        buyCity.setText(mCarItems.get(position).getCity()+",");
        buyState.setText( mCarItems.get(position).getState());
        //define the shape of favorite
        if(mCarItems.get(position).getIsfav()){
            favorite.setImageResource(R.mipmap.ic_favorite);
        }else{
            favorite.setImageResource(R.mipmap.ic_favorite_border);
        }

        //favorite icon click issue
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCarItems.get(position).getIsfav()) {
                    _car_id = String.valueOf(mCarItems.get(position).getId());
                    addFav = "http://cartopia.club/api/favs";
                    new addFavorite(v).execute(addFav, _car_id, position+"");
//                    favorite.setImageResource(R.mipmap.ic_favorite);

                }else{
                    _car_id = String.valueOf(mCarItems.get(position).getId());
                    delFav = "http://cartopia.club/api/favdestroy?car_id="+_car_id+"&user_id=";
                    new delFavorite(v).execute(delFav, _car_id, position+"");
//                    favorite.setImageResource(R.mipmap.ic_favorite_border);

                }
            }
        });
        return view;
    }


        //Add Favorite, use AsyncTask to run JsonParse on a different thread
    private class addFavorite extends AsyncTask<String, String, JSONObject> {
        int position;
            private View view;
            public addFavorite(View v){
                this.view = v;
            }
        @Override
        protected JSONObject doInBackground(String... params) {
            position = Integer.parseInt(params[2]);
            //get current user_id
            SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String current_user_id = sharedpreferences.getString("Current_User","");
            // defaultHttpClient
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(params[0]);
            JSONObject userObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();
            try {
                // add the car's info into userObj
                userObj.put("car_id", Integer.parseInt(params[1]));
                userObj.put("user_id", Integer.parseInt(current_user_id));
                StringEntity se = new StringEntity(userObj.toString());
                post.setEntity(se);
                // setup the request headers
                post.setHeader("Accept", "application/json");
                post.setHeader("Content-Type", "application/json");
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = client.execute(post, responseHandler);
                json = new JSONObject(response);
            }catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                // Storing  JSON item in a Variable
                String success = json.getString(TAG_SUCCESS);
                if(success.equals("1")){
                    ImageView fav = (ImageView) view.findViewById(R.id.favorite);
                    fav.setImageResource(R.mipmap.ic_favorite);
                    mCarItems.get(position).setIsfav(true);

                }else{
                    onLoginFailed();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            }

    //log in failed operation
    public void onLoginFailed() {
        Toast.makeText(mContext, "save failed", Toast.LENGTH_SHORT).show();
    }

    //Delete Favorite, use AsyncTask to run JsonParse on a different thread
    private class delFavorite extends AsyncTask<String, String, JSONObject> {
        int position;
        private View view;
        public delFavorite(View v){
            this.view = v;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            position = Integer.parseInt(params[2]);
            //get current user_id
            SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String current_user_id = sharedpreferences.getString("Current_User", "");
            String delurl =params[0] + current_user_id;
            JsonParser jParser = new JsonParser();
            //Getting String from URL
            String delstr = jParser.getJsonFromUrl(delurl);
            // Getting JSON from URL
            try {
                del_fav_obj = new JSONObject(delstr);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return del_fav_obj;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                // Storing  JSON item in a Variable
                String success = json.getString(TAG_SUCCESS);
                if(success.equals("2")){
                    ImageView fav = (ImageView) view.findViewById(R.id.favorite);
                    fav.setImageResource(R.mipmap.ic_favorite_border);
                    mCarItems.get(position).setIsfav(false);
                }else{
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}



