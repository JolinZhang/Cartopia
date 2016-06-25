package com.example.jonelezhang.cartopia;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Jonelezhang on 6/19/16.
 */
//realize the same block of action bar and left side nav
public class ToolbarConfiguringActivity extends AppCompatActivity {
    //tool bar
    private Toolbar toolbar;
    private ImageView toolbar_logo;
    //left side navigation
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    public void configureToolbar() {
        //set toolbar and toolbar logo
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_logo = (ImageView) findViewById(R.id.toolbar_logo);

        //show list for left side nav list
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        //Getting reference to the DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navList);
        //Creating an ArrayAdapter to add items to mDrawerList
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles);
        // Setting the adapter to mDrawerList
        mDrawerList.setAdapter(adapter);

        //toolbar logo click issue
        toolbar_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click nav icon appear , then click disappear
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                // Set the list's click listener
                mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0:
                                Intent i = new Intent(getBaseContext(), Buy.class);
                                startActivity(i);
                                break;
                            case 1:
                                Intent i1 = new Intent(getBaseContext(), Sell.class);
                                startActivity(i1);
                                break;
                            case 2:
                                Intent i2 = new Intent(getBaseContext(), Info.class);
                                startActivity(i2);
                                break;
                            case 3:
                                Intent i3 = new Intent(getBaseContext(), MyCars.class);
                                startActivity(i3);
                                break;
                            case 4:
                                Intent i4 = new Intent(getBaseContext(), MyFavs.class);
                                startActivity(i4);
                                break;
                            case 5:
                                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
//                                String id = sharedpreferences.getString("Current_User","");
                                editor.clear();
                                editor.commit();
                                Intent i5 = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i5);
                                break;
                        }
                    }
                });
            }
        });
    }
}
