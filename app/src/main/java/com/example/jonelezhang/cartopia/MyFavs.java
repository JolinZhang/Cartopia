package com.example.jonelezhang.cartopia;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MyFavs extends ToolbarConfiguringActivity {
    //widget on actionbar
    private TextView toolbar_title;
    private TextView toolbar_filter;

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


        //realization of view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.favspager);
        vpPager.setAdapter(new MyFavsFragmentPagerAdapter(getSupportFragmentManager(), MyFavs.this));
        //Customize the Animation with PageTransformer
        vpPager.setPageTransformer(true, new MyFavsZoomOutPageTransformer());

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
