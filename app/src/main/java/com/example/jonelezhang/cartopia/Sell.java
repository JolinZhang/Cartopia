package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class Sell extends ToolbarConfiguringActivity {
    //widget on actionbar
    private TextView toolbar_title;
    private TextView toolbar_filter;
    //initial spinner and
    private Spinner year;
    private Spinner state;
    private EditText make;
    private EditText model;
    private  EditText city;
    private EditText mileage;
    private EditText price;
    private EditText contact;
    private EditText note;
    private Button  choose_photo;
    private Button sell_submit;
    //set string value for each widget
    private  String _year;
    private String _make;
    private String _model;
    private String _mileage;
    private String _price;
    private String _state;
    private String _city;
    private String _contact;
    private String _notes;
    private String user_id;
    //initial sharedPreference to set Token
    private SharedPreferences mPreferences;
    private static final String sell_url = "http://cartopia.club/api/cars";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        //initial action toolbar and set action bar title
        configureToolbar();
        //set toolbar title for sell activity and invisible filter block.
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("SELL");
        toolbar_filter = (TextView) findViewById(R.id.toolbar_filter);
        toolbar_filter.setVisibility(View.INVISIBLE);

        //set adapter for the year spinner
        year = (Spinner) findViewById(R.id.sell_year);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this,R.array.year_array,R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter_year);
        //set spinner's click listener
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set adapter for the year spinner
        state = (Spinner) findViewById(R.id.sell_state);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_state = ArrayAdapter.createFromResource(this,R.array.state_array,R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter_state);
        //set spinner's click listener
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _state = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //get string value of all editText except year state picture
        make = (EditText) findViewById(R.id.sell_make);
        model = (EditText) findViewById(R.id.sell_model);
        mileage = (EditText) findViewById(R.id.sell_mileage);
        price = (EditText) findViewById(R.id.sell_price);
        city = (EditText) findViewById(R.id.sell_city);
        contact = (EditText) findViewById(R.id.sell_contact);
        note = (EditText) findViewById(R.id.sell_note);


        //get user id string
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        user_id = sharedpreferences.getString("Current_User", "");
        editor.commit();

        //get picture name into string  when click choose picture button
        choose_photo = (Button) findViewById(R.id.sell_choose_photo);
        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });

        //car sell submit button click issue
        sell_submit = (Button) findViewById(R.id.sell_submit);
        sell_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _make = make.getText().toString();
                _model = model.getText().toString();
                _mileage = mileage.getText().toString();
                _price = price.getText().toString();
                _city = city.getText().toString();
                _contact = contact.getText().toString();
                _notes = note.getText().toString();

                //check validation of the value in sell table
                if(sell_validate(_year,_make,_model,_mileage,_price,_city, _state,_contact)) {
                    //execute post json
                    new Sell_JSONParse().execute(sell_url);
                }
            }
        });



        }
    //use AsyncTask to run JsonParse on a different thread to realize submit post method
    private class Sell_JSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... urls) {
            // defaultHttpClient
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject userObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();
            try {
                try {
                    // setup the returned values in case
                    // something goes wrong
                    json.put("success", false);
                    json.put("info", "Something went wrong. Retry!");

                    // add the car's info into userObj
                    userObj.put("year", Integer.parseInt(_year));
                    userObj.put("make", _make);
                    userObj.put("model", _model);
                    userObj.put("mileage", Integer.parseInt(_mileage));
                    userObj.put("price", Integer.parseInt(_price));
                    userObj.put("contact", _contact);
                    userObj.put("city", _city);
                    userObj.put("state", _state);
                    userObj.put("notes", _notes);
                    userObj.put("picture", "default.jpg");
                    userObj.put("issold", false);
                    userObj.put("user_id", Integer.parseInt(user_id));
                    StringEntity se = new StringEntity(userObj.toString());
                    post.setEntity(se);

                    // setup the request headers
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                }catch(IOException e){
                    e.printStackTrace();

                }
           }catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
               }

            return json;
        }
        @Override
        protected  void onPostExecute(JSONObject json){
            try {
                if (json.getBoolean("success")) {
                    // everything is ok
                    SharedPreferences.Editor editor = mPreferences.edit();
                    // save the returned auth_token into
                    // the SharedPreferences
                    editor.putString("AuthToken", json.getJSONObject("data").getString("auth_token"));
                    editor.commit();

                    // launch the HomeActivity and close this one
                    Intent intent = new Intent(getApplicationContext(), Buy.class);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(getBaseContext(), json.getString("info"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // something went wrong: show a Toast
                // with the exception message
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }

    }


    public boolean sell_validate(String vaYear, String vaMake, String vaModel, String vaMileage, String vaPrice,
                                 String vaCity, String vaState, String vaContact ){
        boolean valid = true;
        if(vaYear.isEmpty()){
            Toast.makeText(getBaseContext(), " year can not be empty", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(vaMake.isEmpty()){
            make.setError("can not be empty");
            valid = false;
        }
        if(vaModel.isEmpty()){
            model.setError("can not be empty");
            valid = false;

        }
        if(vaMileage.isEmpty()){
            mileage.setError("can not be empty");
            valid = false;

        }
        if(vaPrice.isEmpty()){
            price.setError("can not be empty");
            valid = false;

        }
        if(vaCity.isEmpty()){
            city.setError("can not be empty");
            valid = false;

        }
        if(vaState.isEmpty()){
            Toast.makeText(getBaseContext(), " state can not be empty", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(vaContact.isEmpty()){
            contact.setError("can not be empty");
            valid = false;

        }
        return valid;
    }






    //get file path from the gallery
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(resultCode == RESULT_OK && requestCode == 1){
//            Uri selectedImage = data.getData();
//            //addPhoto.setImageURI(selectedImage);
//            String[] filePath = {MediaStore.Images.Media.DATA};
//            Cursor c = getContentResolver().query(selectedImage, filePath,null,null,null);
//            c.moveToFirst();
//            int columnIndex = c.getColumnIndex(filePath[0]);
//            String picturePath=c.getString(columnIndex);
//            c.close();
//            File imgFile = new  File(picturePath);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_sell, menu);
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
