package com.example.jonelezhang.cartopia;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Buy extends AppCompatActivity {
    //tool bar
    private Toolbar toolbar;
    //left side navigation
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<NavDrawerItem> mNavItems;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    //right sort navigation
    private String[] sPlanetTitles;
    private TypedArray img;

    private DrawerLayout sDrawerLayout;
    private ListView sDrawerList;

    private ImageView sort_icon;
    //buy car list
    private GridView gridView;
    private ArrayList<BuyCarItem> carItems;
    private BuyCarItem buyCar;
    private CarListAdapter adapter;
    //JSON
    private String url;
    private JSONArray cars;
    private String strUrl;
    static JSONArray obj = null;
    //JSON Node Names
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

        //tool bar setting
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(null);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //right show sort nav list array of string and icon
        sPlanetTitles = getResources().getStringArray(R.array.sort_array);
        img = getResources().obtainTypedArray(R.array.icon_sort_array);
        sDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // set adapter for right sort nav list
        SortListAdapter sort_adapter = new SortListAdapter(this, sPlanetTitles, img);
        sDrawerList = (ListView) findViewById(R.id.sortList);
        sDrawerList.setAdapter(sort_adapter);

        //show list for left side nav list
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        //Getting reference to the DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navList);
        //Creating an ArrayAdapter to add items to mDrawerList
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles);
        // Setting the adapter to mDrawerList
        mDrawerList.setAdapter(adapter);

////        /* Getting reference to the ActionBarDrawerToggle */
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.drawer_open, R.string.drawer_close);
//        mDrawerLayout.setDrawerListener(toggle);

        //toolbar click issue
        toolbar.setNavigationIcon(R.drawable.ic_list_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click nav icon appear , then click disappear
                if( mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }else{
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
//                // Set the adapter for the list view
//                mDrawerList.setAdapter(new DrawerListAdapter(Buy.this, mNavItems));

//                // Set the list's click listener
//                mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                        switch (pos){
//                            case 0:
//                                Intent i = new Intent(MainActivity.this,Aluminium.class);
//                                startActivity(i);
//                                break;
//                            case 1:
//                                Intent i2 = new Intent(MainActivity.this,Gold.class);
//                                startActivity(i2);
//                                break;
//                            case 2:
//                                Intent i3 = new Intent(MainActivity.this,Zinc.class);
//                                startActivity(i3);
//                                break;
//                        }
//
//                    }
//                });
            }
        });

        // get json data for car buy list
        new BuyJSONParse().execute();

    }
    //use AsyncTask to run JsonParse on a different thread
    private class BuyJSONParse extends AsyncTask<String, String, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... args) {
            url = "http://cartopia.club/api/cars";
            JsonParser jParser = new JsonParser();
            // Getting JSON from URL
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
                // Storing  JSON item in a Variable
                carItems = new ArrayList<>();
                if(json != null){
                    for(int i=0; i<json.length(); i++) {
                        JSONObject finalObject = json.getJSONObject(i);
                        buyCar = new BuyCarItem();
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
