package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jonelezhang on 7/1/16.
 */
public class MyFavsPageFragment extends Fragment {
    public static final String ARG_PAGE = "page";
    private int mPageNumber;
    Context mContext;
    // Favs json
    private String url;
    private String strUrl;
    static JSONObject obj = null;
    private ArrayList<BuyCarItem> Items;
    private String count;
    private JSONArray favsItems;
    private BuyCarItem favsCar;
    private BuyCarItem ee;
    //JSON data
    private static final String TAG_COUNT = "count";
    private static final String TAG_FAVS = "favs";
    private static final String TAG_PICTURE = "picture";
    private static final String TAG_CAR_ID = "car_id";

    private ImageView  tv;

    public static MyFavsPageFragment create(int pageNumber) {
        MyFavsPageFragment fragment = new MyFavsPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public MyFavsPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.my_favs_fragment_page, container, false);

        // Set the title view to show the page number.
        tv = (ImageView) rootView.findViewById(R.id.favs_image);
//        tv.setText((mPageNumber + 1) + "");

        new FavsJSONParse().execute();

        return rootView;
    }

    private class FavsJSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String user_id = sharedpreferences.getString("Current_User", "");
            JsonParser jParser = new JsonParser();
            url = "http://cartopia.club/api/favs?user_id="+ user_id;
            strUrl = jParser.getJsonFromUrl(url);
            // Getting JSON from URL
            try {
                obj = new JSONObject(strUrl);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return obj;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                count  = json.getString(TAG_COUNT);
                Items = new ArrayList<>();
                favsItems = json.getJSONArray(TAG_FAVS);
                if(json != null){
                    for(int i=0; i<json.length(); i++) {
                        JSONObject finalObject = favsItems.getJSONObject(i);
                        favsCar = new BuyCarItem();
                        favsCar.setCar_id(finalObject.getInt(TAG_CAR_ID));
                        favsCar.setImageResourceId(finalObject.getString(TAG_PICTURE));
                        Items.add(favsCar);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetImage.getImage(tv,Items.get(mPageNumber));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ee = Items.get(mPageNumber);
                    Intent i = new Intent(getActivity(), CarDetails.class);
                    i.putExtra("id", ee.getCar_id() + "");
                    startActivity(i);
                }
            });
        }


    }



}
