package com.example.jonelezhang.cartopia;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private View tview;
    private View mContentView;
    private View mLoadingView;
    private View myView;
    private int mMediumAnimationDuration;
    private boolean mContentLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // dynamic change between sign up and log on
        tv = (TextView)findViewById(R.id.text_signup);
        tview = findViewById(R.id.text_signup);
        myView = findViewById(R.id.main_activity);
        mContentView = findViewById(R.id.logon_box);
        mLoadingView = findViewById(R.id.signup_box);

        // Initially hide the content view.
        mLoadingView.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.
        mMediumAnimationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentLoaded = !mContentLoaded;
                showContentOrLoadingIndicator(mContentLoaded);
            }
        });

        if(mContentLoaded == false) {
            mContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Layout has happened here.
                    int s = mContentView.getBottom();
                    tv.setY(s + 5);
                    // Don't forget to remove your listener when you are done with it.
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
        if(mContentLoaded == true){
            mLoadingView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Layout has happened here.
                    int s = mLoadingView.getBottom();
                    tv.setY(s + 5);
                    // Don't forget to remove your listener when you are done with it.
                    mLoadingView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }


    }


    private void showContentOrLoadingIndicator(boolean contentLoaded) {
        // Decide which view to hide and which to show.

        final View showView = contentLoaded ? mLoadingView : mContentView;
        final View hideView = contentLoaded ? mContentView : mLoadingView;

        // Set the "show" view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        showView.setAlpha(0f);
        showView.setVisibility(View.VISIBLE);


        showView.animate()
                .alpha(1f)
                .setDuration(mMediumAnimationDuration)
                .setListener(null);

        // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
        // to GONE as an optimization step (it won't participate in layout passes, etc.)
        hideView.animate()
                .alpha(0f)
                .setDuration(mMediumAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideView.setVisibility(View.GONE);
                    }
                });

    }

//    @Override
//    protected void onResume()
//    {
//        // TODO Auto-generated method stub
//        super.onResume();
//
//        int s = mContentView.getBottom();
//        int d = mLoadingView.getBottom();
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
