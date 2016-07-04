package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyFavs extends ToolbarConfiguringActivity {
    //widget on actionbar
    private TextView toolbar_title;
    private TextView toolbar_filter;
    // Favs json
    private String url;
    private String strUrl;
    static JSONObject obj = null;
    private ArrayList<BuyCarItem> Items;
    private int count;
    private JSONArray favsItems;
    private BuyCarItem favsCar;
    //JSON data
    private static final String TAG_COUNT = "count";
    private static final String TAG_FAVS = "favs";
    private static final String TAG_PICTURE = "picture";
    private static final String TAG_CAR_ID = "car_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favs);
        //initial action toolbar and set action bar title
        configureToolbar();
        //set toolbar title for sell activity and invisible filter block.
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("MY FAVS");
        toolbar_filter = (TextView) findViewById(R.id.toolbar_filter);
        toolbar_filter.setVisibility(View.INVISIBLE);


        // Show the picture of the favs image
        new FavsJSONParse().execute();

    }

    private class FavsJSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
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
                count  = Integer.parseInt(json.getString(TAG_COUNT));
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

            //realization of view pager
            ViewPager vpPager = (ViewPager) findViewById(R.id.favspager);
            vpPager.setAdapter(new MyFavsFragmentPagerAdapter(getSupportFragmentManager(), MyFavs.this, count, Items));

            //Customize the Animation with PageTransformer
            vpPager.setPageTransformer(true, new MyFavsZoomOutPageTransformer());

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_favs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
