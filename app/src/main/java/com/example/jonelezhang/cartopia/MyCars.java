package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyCars extends ToolbarConfiguringActivity {
    //widget on actionbar
    private TextView toolbar_title;
    private TextView toolbar_filter;
    private ViewPager viewPager;
    //Mycars json
    private String url;
    private String strUrl;
    static JSONArray obj = null;
    //json data
    private ArrayList<BuyCarItem> Items;
    private BuyCarItem myCars;
    private int count;
    private static final String TAG_PICTURE = "picture";
    private static final String TAG_CAR_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);
        //initial action toolbar and set action bar title
        configureToolbar();
        //set toolbar title for sell activity and invisible filter block.
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("MY CARS");
        toolbar_filter = (TextView) findViewById(R.id.toolbar_filter);
        toolbar_filter.setVisibility(View.INVISIBLE);

        // Show the picture of the cars image
        new CarsJSONParse().execute();

    }

    //get info in mycars
    private class CarsJSONParse extends AsyncTask<String, String, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String user_id = sharedpreferences.getString("Current_User", "");
            JsonParser jParser = new JsonParser();
            url = "http://cartopia.club/api/cars?user_id=" + user_id;
            strUrl = jParser.getJsonFromUrl(url);
            // Getting JSON from URL
            try {
                obj = new JSONArray(strUrl);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return obj;
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            try {
                Items = new ArrayList<>();
                if (json != null) {
                    count = json.length();
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject finalObject = json.getJSONObject(i);
                        myCars = new BuyCarItem();
                        myCars.setCar_id(finalObject.getInt(TAG_CAR_ID));
                        myCars.setImageResourceId(finalObject.getString(TAG_PICTURE));
                        Items.add(myCars);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Get the ViewPager and set it's PagerAdapter so that it can display items
            viewPager = (ViewPager) findViewById(R.id.mycarspager);
            viewPager.setClipToPadding(false);
            viewPager.setPageMargin(20);
            viewPager.setOffscreenPageLimit(5);
            viewPager.setAdapter(new MyCarsFragmentPagerAdapter(getSupportFragmentManager(), MyCars.this,count,Items));


        }
    }





        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_cars, menu);
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
