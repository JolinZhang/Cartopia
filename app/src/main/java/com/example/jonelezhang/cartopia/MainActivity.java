package com.example.jonelezhang.cartopia;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private View mContentView;
    private View mLoadingView;
    private int mMediumAnimationDuration;
    private boolean mContentLoaded;

    private EditText _login_username;
    private EditText _login_passport;
    private Button _loginButton;

    private EditText _singup_username;
    private EditText _signup_email;
    private EditText _signup_passport;
    private EditText _signup_confirm_passport;
    private Button _signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // dynamic change between sign up and log on
        tv = (TextView)findViewById(R.id.text_signup);

        mContentView = findViewById(R.id.logon_box);
        mLoadingView = findViewById(R.id.signup_box);

        // Initially hide the content view.
        mLoadingView.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.
        mMediumAnimationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        // animation between sign up and log on
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentLoaded = !mContentLoaded;
                showContentOrLoadingIndicator(mContentLoaded);
                showSignupIndicator(mContentLoaded);
            }
        });


        //get log in widgets
        _login_username = (EditText) findViewById(R.id.logon_username);
        _login_passport = (EditText) findViewById(R.id.logon_passport);
        _loginButton = (Button) findViewById(R.id.logon_btn);

        // log in button operations
        _loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //get sign up widgets
        _singup_username = (EditText) findViewById(R.id.signup_username);
        _signup_email = (EditText) findViewById(R.id.signup_email);
        _signup_passport = (EditText) findViewById(R.id.signup_passport);
        _signup_confirm_passport = (EditText) findViewById(R.id.signup_confirm_passport);
        _signup_btn = (Button) findViewById(R.id.signup_btn);

        //sign up button operations
        _signup_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               
            }
        });
    }

    //log in function
    public void login() {
        if (!login_validate()) {
            onLoginFailed();
            return;
        }

        //login success operation
        _loginButton.setEnabled(false);
    }

    //log in failed operation
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
        _loginButton.setEnabled(true);
    }

    //validation of username and passport
    public boolean login_validate(){
        boolean valid = true;
        String login_username = _login_username.getText().toString();
        String login_passport = _login_passport.getText().toString();

        if(login_username.isEmpty()){
            _login_username.setError("can not be empty");
            valid = false;
        }else if(login_username.length() >= 20){
            _login_username.setError("at most 20 characters");
            valid = false;
        }else{
            _login_username.setError(null);
        }

        if(login_passport.isEmpty()){
            _login_passport.setError("can not be empty");
            valid = false;
        }else if(login_passport.length() < 6){
            _login_passport.setError("at least 6 characters");
            valid = false;
        }else{
            _login_passport.setError(null);
        }
        return valid;
    }




  // animation  of sign up login text indicator
   private void showSignupIndicator(boolean contentLoaded){
       // redesign the position of text of sign up and log in
       if(contentLoaded == false) {
           mContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
               @Override
               public void onGlobalLayout() {
                   // Layout has happened here.
                   int s = mContentView.getBottom();
                   tv.setY(s + 5);
                   tv.setText("No account yet? Create one");
                   // Don't forget to remove your listener when you are done with it.
                   mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
               }
           });
       }
       if(contentLoaded == true){
           mLoadingView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
               @Override
               public void onGlobalLayout() {
                   // Layout has happened here.
                   int s = mLoadingView.getBottom();
                   tv.setY(s + 5);
                   tv.setText("Already a member? Login");
                   // Don't forget to remove your listener when you are done with it.
                   mLoadingView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
               }
           });
       }
   }

    // animation of changing sign up and log in
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
