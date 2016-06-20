package com.example.jonelezhang.cartopia;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Jonelezhang on 6/19/16.
 */
//realize the same block of action bar and left side nav
public class ToolbarConfiguringActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<NavDrawerItem> mNavItems;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    public void configureToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

//        /* Getting reference to the ActionBarDrawerToggle */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(toggle);

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
                // Set the list's click listener
                mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position){
                            case 0:
                                Intent i = new Intent(getBaseContext(),Buy.class);
                                getSupportActionBar().setTitle("BUY");
                                startActivity(i);
                                break;
                            case 1:
                                Intent i2 = new Intent(getBaseContext(),Sell.class);
                                getSupportActionBar().setTitle("SELL");
                                startActivity(i2);
                                break;
                        }
                    }
                });
            }
        });

    }

}
