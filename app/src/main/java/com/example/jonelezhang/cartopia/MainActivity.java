package com.example.jonelezhang.cartopia;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private TextView logon_tv;
    private TextView signup_tv;
    private View mContentView;
    private View mLoadingView;
    private int mMediumAnimationDuration;

    private EditText _login_username;
    private EditText _login_password;
    private Button _loginButton;

    private EditText _singup_username;
    private EditText _signup_email;
    private EditText _signup_password;
    private EditText _signup_confirm_password;
    private Button _signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // dynamic change between sign up and log on
        logon_tv = (TextView)findViewById(R.id.text_logon);

        // dynamic change between sign up and log on
        signup_tv = (TextView)findViewById(R.id.text_signup);

        mContentView = findViewById(R.id.logon_box);
        mLoadingView = findViewById(R.id.signup_box);

        // Initially hide the content view.
        mLoadingView.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.
        mMediumAnimationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        // animation between sign up and log on
        logon_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the "show" view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                mLoadingView.setAlpha(0f);
                mLoadingView.setVisibility(View.VISIBLE);

                mLoadingView.animate()
                        .alpha(1f)
                        .setDuration(mMediumAnimationDuration)
                        .setListener(null);

                // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
                // to GONE as an optimization step (it won't participate in layout passes, etc.)
                mContentView.animate()
                        .alpha(0f)
                        .setDuration(mMediumAnimationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mContentView.setVisibility(View.GONE);
                            }
                        });
            }
        });

        // animation between sign up and log on
        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the "show" view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                mContentView.setAlpha(0f);
                mContentView.setVisibility(View.VISIBLE);

                mContentView.animate()
                        .alpha(1f)
                        .setDuration(mMediumAnimationDuration)
                        .setListener(null);

                // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
                // to GONE as an optimization step (it won't participate in layout passes, etc.)
                mLoadingView.animate()
                        .alpha(0f)
                        .setDuration(mMediumAnimationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mLoadingView.setVisibility(View.GONE);
                            }
                        });




            }
        });

        //get log in widgets
        _login_username = (EditText) findViewById(R.id.logon_username);
        _login_password = (EditText) findViewById(R.id.logon_password);
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
        _signup_password = (EditText) findViewById(R.id.signup_password);
        _signup_confirm_password = (EditText) findViewById(R.id.signup_confirm_password);
        _signup_btn = (Button) findViewById(R.id.signup_btn);

        //sign up button operations
        _signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }


    //log in function
    public void login() {
        //get content of login username and password
        String login_username = _login_username.getText().toString();
        String login_password = _login_password.getText().toString();

        if (!login_validate(login_username, login_password)) {
            onLoginFailed();
            return;
        }

        //login success operation
        _loginButton.setEnabled(false);

//        String readJSON = login_connectWithHttpGet(login_username, login_password);
//        try{
//            JSONObject jsonObject = new JSONObject(readJSON);
//            Log.i(MainActivity.class.getName(), jsonObject.getString("id"));
//        } catch(Exception e){e.printStackTrace();}



    }

    //login connect with http get method
//    private String login_connectWithHttpGet(String givenUsername, String givenPassword) {

//         class HttpGetAsyncTask extends AsyncTask<String, Void, String>{
//             @Override
//             protected String doInBackground(String... params) {
//                 String paramUsername = params[0];
//                 String paramPassword = params[1];
//                 HttpClient httpClient = new DefaultHttpClient();
//                 HttpGet httpGet = new HttpGet("http://localhost:3000/api/login?username=" + paramUsername + "&password=" + paramPassword );
//                 try{
//                     HttpResponse httpResponse = httpClient.execute(httpGet);
//                     InputStream inputStream = httpResponse.getEntity().getContent();
//                     InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                     StringBuilder stringBuilder = new StringBuilder();
//                     String bufferedStrChunk = null;
//                     while((bufferedStrChunk = bufferedReader.readLine()) != null){
//                         stringBuilder.append(bufferedStrChunk);
//                     }
//                     return stringBuilder.toString();
//                 }catch (ClientProtocolException cpe) {
//
//                 }catch (IOException ioe){
//
//                 }
//                 return  null;
//             }
//         }

            // Create http cliient object to send request to server
//            HttpClient Client = new DefaultHttpClient();
//            StringBuilder builder = new StringBuilder();
//            // Create URL string
//            String URL = "http://localhost:3000/api/login?username=" + givenUsername + "&password=" + givenPassword;
//
//            try
//            {
//                HttpGet httpget = new HttpGet(URL);
//                HttpResponse response = Client.execute(httpget);
//                int statusCode = response.getStatusLine().getStatusCode();
//                if(statusCode == 200) {
//                    InputStream inputStream = response.getEntity().getContent();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        builder.append(line);
//                    }
//                }else{
//                    Log.e("123","Failedet JSON object");
//                }
//            }catch(ClientProtocolException e){
//                e.printStackTrace();
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        return builder.toString();
//
//        }



    //log in failed operation
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
        _loginButton.setEnabled(true);
    }

    //validation of username and password
    public boolean login_validate(String login_username, String login_password){
        boolean valid = true;

        if(login_username.isEmpty()){
            _login_username.setError("can not be empty");
            valid = false;
        }else if(login_username.length() >= 20){
            _login_username.setError("at most 20 characters");
            valid = false;
        }else{
            _login_username.setError(null);
        }

        if(login_password.isEmpty()){
            _login_password.setError("can not be empty");
            valid = false;
        }else if(login_password.length() < 6){
            _login_password.setError("at least 6 characters");
            valid = false;
        }else{
            _login_password.setError(null);
        }
        return valid;
    }



    //sign up function
    public void signup() {
        if (!signup_validate()) {
            onsignupFailed();
            return;
        }

        //signup success operation
        _signup_btn.setEnabled(false);
    }

    //sign up  failed operation
    public void onsignupFailed() {
        Toast.makeText(getBaseContext(), "Sign Up failed", Toast.LENGTH_SHORT).show();
        _signup_btn.setEnabled(true);
    }

    //validation of sign up username, email and password
    public boolean signup_validate(){
        boolean valid = true;
        String signup_username = _singup_username.getText().toString();
        String signup_email = _signup_email.getText().toString();
        String signup_password= _signup_password.getText().toString();
        String signup_confirm_password = _signup_confirm_password.getText().toString();
        //username valid
        if(signup_username.isEmpty()){
            _singup_username.setError("can not be empty");
            valid = false;
        }else if(signup_username.length() >= 20){
            _singup_username.setError("at most 20 characters");
            valid = false;
        }else{
            _singup_username.setError(null);
        }
        //email valid
        if (signup_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(signup_email).matches()) {
            _signup_email.setError("enter a valid email address");
            valid = false;
        } else {
            _signup_email.setError(null);
        }
       // password valid
        if(signup_password.isEmpty()){
            _signup_password.setError("can not be empty");
            valid = false;
        }else if(signup_password.length() < 6){
            _signup_password.setError("at least 6 characters");
            valid = false;
        }else{
            _signup_password.setError(null);
        }
        //confirm password valid
        if(signup_confirm_password.isEmpty()){
            _signup_confirm_password.setError("can not be empty");
            valid = false;
        }else if(! signup_confirm_password.equals(signup_password)){
            _signup_confirm_password.setError("not equal to password");
            valid = false;
        }else{
            _signup_confirm_password.setError(null);
        }
        return valid;
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
