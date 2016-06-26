package com.example.jonelezhang.cartopia;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Buy extends ToolbarConfiguringActivity{
    //action bar title
    private TextView toolbar_title;
    private TextView toolbar_filter;
    private DrawerLayout mDrawerLayout;
    //right sort navigation
    private String[] sPlanetTitles;
    private TypedArray img;
    private ListView sDrawerList;
    //right sort list item select
    private String sort_list_item = "";
    //buy car list
    private GridView gridView;
    private ArrayList<BuyCarItem> carItems;
    private BuyCarItem buyCar;
    private BuyCarItem ee;
    //JSON
    private String url;
    private JSONArray cars;
    private String strUrl;
    static JSONArray obj = null;
    //JSON Node Names
    private static final String TAG_ID = "id";
    private static final String TAG_PICTURE = "picture";
    private static final String TAG_YEAR = "year";
    private static final String TAG_MAKE = "make";
    private static final String TAG_MODEL = "model";
    private static final String TAG_PRICE = "price";
    private static final String TAG_MILEAGE = "mileage";
    private static final String TAG_CITY = "city";
    private static final String TAG_STATE = "state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        //action bar and left side nav realization
        configureToolbar();
        //set action bar content
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("BUY");
        //set animation of action tool bar filter
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar_filter = (TextView) findViewById(R.id.toolbar_filter);
        toolbar_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click nav icon appear , then click disappear
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });


        // get json data for car buy list
        new BuyJSONParse().execute();

        //right show sort nav list array of string and icon
        sPlanetTitles = getResources().getStringArray(R.array.sort_array);
        img = getResources().obtainTypedArray(R.array.icon_sort_array);
        // set adapter for right sort nav list
        SortListAdapter sort_adapter = new SortListAdapter(this, sPlanetTitles, img);
        sDrawerList = (ListView) findViewById(R.id.sortList);
        sDrawerList.setAdapter(sort_adapter);

        // Set the list's click listener
        sDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        sort_list_item = "0";
                        break;
                    case 1:
                        sort_list_item = "1";
                        break;
                    case 2:
                        sort_list_item = "2";
                        break;
                    case 3:
                        sort_list_item = "3";
                        break;
                    case 4:
                        sort_list_item = "4";
                        break;
                    case 5:
                        sort_list_item = "5";
                        break;
                    case 6:
                        sort_list_item = "6";
                        break;
                    case 7:
                        sort_list_item = "7";
                        break;
                }
                new BuyJSONParse().execute();
            }
        });
    }
    //use AsyncTask to run JsonParse on a different thread
    private class BuyJSONParse extends AsyncTask<String, String, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... args) {
            JsonParser jParser = new JsonParser();
            // Getting JSON from URL
            if(sort_list_item.length() == 0){
                //url for right sort list
                url = "http://cartopia.club/api/cars";
            }else{
                url = "http://cartopia.club/api/sort?sort="+sort_list_item;
            }
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
            //get car id value
            try {
                // Storing  JSON item in a Variable, define an ArrayList carItems to store all cars' info.
                carItems = new ArrayList<>();
                if(json != null){
                    for(int i=0; i<json.length(); i++) {
                        JSONObject finalObject = json.getJSONObject(i);
                        buyCar = new BuyCarItem();
                        buyCar.setId(Integer.parseInt(finalObject.getString(TAG_ID)));
                        buyCar.setImageResourceId(finalObject.getString(TAG_PICTURE));
                        buyCar.setPrice(Integer.parseInt(finalObject.getString(TAG_PRICE)));
                        buyCar.setMileage(Integer.parseInt(finalObject.getString(TAG_MILEAGE)));
                        buyCar.setYear(Integer.parseInt(finalObject.getString(TAG_YEAR)));
                        buyCar.setMake(finalObject.getString(TAG_MAKE));
                        buyCar.setModel(finalObject.getString(TAG_MODEL));
                        buyCar.setCity(finalObject.getString(TAG_CITY));
                        buyCar.setState(finalObject.getString(TAG_STATE));
                        carItems.add(buyCar);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //show car list on gridview
            gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(new CarListAdapter(Buy.this, carItems));
            //click item to transparent car id.
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // pass car id to CarDetails activity
                    ee = carItems.get(position);
                    Intent i = new Intent(Buy.this, CarDetails.class);
                    i.putExtra("id", ee.getId()+"");
                    startActivity(i);
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buy, menu);
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
