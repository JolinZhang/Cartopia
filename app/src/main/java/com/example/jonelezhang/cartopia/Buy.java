package com.example.jonelezhang.cartopia;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Buy extends AppCompatActivity {
//    tool bar
    private Toolbar toolbar;
//    side navigation
//    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<NavDrawerItem> mNavItems;
//    buy car list
    private GridView gridView;
    private ArrayList<BuyCarItem> carItems;
    private BuyCarItem buyCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

//        tool bar setting
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

//        data for side navigation
        mNavItems = new ArrayList<>();
        mNavItems.add(new NavDrawerItem("BUY"));
        mNavItems.add(new NavDrawerItem("SELL"));
        mNavItems.add(new NavDrawerItem("INFO"));
        mNavItems.add(new NavDrawerItem("MY CARS"));
        mNavItems.add(new NavDrawerItem("MY FAVS"));
        mNavItems.add(new NavDrawerItem("LOG OUT"));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navList);

//        data for car buy list
        carItems = new ArrayList<>();
        buyCar = new BuyCarItem();
        buyCar.setId(1);
        buyCar.setImageResourceId("1");
        buyCar.setPrice(9000);
        buyCar.setMileage(98799);
        buyCar.setYear(1995);
        buyCar.setModel("MINI");
        buyCar.setMake("Cooper");
        buyCar.setCity("Cupertino");
        buyCar.setState("CA");
        carItems.add(buyCar);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new CarListAdapter(Buy.this,carItems));

//        toolbar click issue
        toolbar.setNavigationIcon(R.mipmap.ic_list_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPlanetTitles = getResources().getStringArray(R.array.planets_array);

                if( mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }else{
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                // Set the adapter for the list view
                mDrawerList.setAdapter(new DrawerListAdapter(Buy.this, mNavItems));

                // Set the list's click listener
                mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

                    }
                });

            }
        });

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
