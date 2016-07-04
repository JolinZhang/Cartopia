package com.example.jonelezhang.cartopia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarDetails extends AppCompatActivity {
    //widget on actionbar
    private TextView toolbar_filter;
    //JSON
    private String url;
    private String strUrl;
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
    private static final String TAG_CONTACT = "contact";
    private static final String TAG_NOTES = "notes";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_CREATEDAT = "created_at";
    // widgets on car details
    private  TextView carDetailsPrice;
    private TextView carDetailsMileage;
    private TextView carDetailsYear;
    private TextView carDetailsMake;
    private TextView carDetailsModel;
    private  TextView carDetailsCity;
    private TextView carDetailsState;
    private TextView carDetailsUser;
    private TextView carDetailsContact;
    private TextView carDetailsNotes;
    private TextView carDetailsCreatedAt;
    private  ImageView carDetailsImage;
    //comment JSON
    private String commentUrl;
    private String commentStrUrl;
    //comment JSON Node Names
    private static final String TAG_CID = "id";
    private static final String TAG_CCONTENT = "content";
    private static final String TAG_CUSER_ID = "user_id";
    //widgets of view
    private CarDetailsGridViewScrollable commentGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        //invisible filter block.
        toolbar_filter = (TextView) findViewById(R.id.toolbar_filter);
        toolbar_filter.setVisibility(View.INVISIBLE);
        //get car id value from buy activity
        final Intent intent = getIntent();
        String car_id = intent.getStringExtra("id");
        url = "http://cartopia.club/api/cars?id="+car_id;
        // get json data for car buy list
        new CarDetailsJSONParse().execute(url);
        //get json data from comment for car details
        commentUrl ="http://cartopia.club/api/comments?car_id="+car_id;
        new CarDetailsCommentJSONParse().execute(commentUrl);

    }
    //use AsyncTask to run JsonParse on a different thread to show comment list
    private class CarDetailsCommentJSONParse extends AsyncTask<String,String,JSONArray>{
        private JSONArray commentObj = null;
        private CarDetailsCommentsItem commentCar;
        private ArrayList<CarDetailsCommentsItem> commentCars;

        @Override
        protected JSONArray doInBackground(String... params) {
            JsonParser jParser  = new JsonParser();
            commentStrUrl = jParser.getJsonFromUrl(params[0]);
            // Getting JSON from URL
            try {
                commentObj = new JSONArray(commentStrUrl);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return commentObj;
        }
        @Override
        protected  void onPostExecute(JSONArray json){
            //get car id value
            try {
                // Storing  JSON item in a Variable, define an ArrayList carItems to store all cars' info.
                commentCars = new ArrayList<>();
                if(json != null){
                    for(int i=0; i<json.length(); i++) {
                        JSONObject finalObject = json.getJSONObject(i);
                        JSONObject finalObjectComment = finalObject.getJSONObject("comments");
                        String finalObjectUser = finalObject.getString("user_name");
                        commentCar = new CarDetailsCommentsItem();
                        commentCar.setId(Integer.parseInt(finalObjectComment.getString(TAG_CID)));
                        commentCar.setContent(finalObjectComment.getString(TAG_CCONTENT));
                        commentCar.setUser_id(Integer.parseInt(finalObjectComment.getString(TAG_CUSER_ID)));
                        commentCar.setUser_name(finalObjectUser);
                        commentCars.add(commentCar);
                    }
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            //show car list on gridview
            commentGridView= (CarDetailsGridViewScrollable) findViewById(R.id.commentView);
            commentGridView.setAdapter(new CarDetailsCommentsAdapter(CarDetails.this, commentCars));
        }

    }

    //use AsyncTask to run JsonParse on a different thread to show car details
    private class CarDetailsJSONParse extends AsyncTask<String, String, JSONObject> {
        private JSONObject obj = null;
        private BuyCarItem buyCar;
        private ArrayList<BuyCarItem> car;
        private JSONArray jsonCar;
        private String jsonUser;

        @Override
        protected JSONObject doInBackground(String... params) {
            JsonParser jParser = new JsonParser();
            strUrl = jParser.getJsonFromUrl(params[0]);
            // Getting JSON from URL
            try {
                obj = new JSONObject(strUrl);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return obj;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            //get car id value
            try {
                // Storing  JSON item in a Variable, define an ArrayList carItems to store all cars' info.
                jsonCar = json.getJSONArray("car");
                jsonUser = json.getString("user_name");
                car = new ArrayList<>();
                if(json != null){
                    for(int i=0; i<jsonCar.length(); i++) {
                        JSONObject finalObject = jsonCar.getJSONObject(i);
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
                        buyCar.setContact(finalObject.getString(TAG_CONTACT));
                        buyCar.setNotes(finalObject.getString(TAG_NOTES));
                        buyCar.setUser_id(Integer.parseInt(finalObject.getString(TAG_USER_ID)));
                        buyCar.setCreatedAt(finalObject.getString(TAG_CREATEDAT));
                        buyCar.setUsername(jsonUser);
                        car.add(buyCar);
                    }
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

            //get all widgets on car details list card
            carDetailsPrice = (TextView) findViewById(R.id.carDetails_price);
            carDetailsMileage = (TextView) findViewById(R.id.carDetails_mileage);
            carDetailsYear = (TextView) findViewById(R.id.carDetails_year);
            carDetailsMake = (TextView) findViewById(R.id.carDetails_make);
            carDetailsModel = (TextView) findViewById(R.id.carDetails_model);
            carDetailsCity = (TextView) findViewById(R.id.carDetails_city);
            carDetailsState = (TextView) findViewById(R.id.carDetails_state);
            carDetailsUser = (TextView) findViewById(R.id.carDetails_user);
            carDetailsContact = (TextView) findViewById(R.id.carDetails_contact);
            carDetailsNotes = (TextView) findViewById(R.id.carDetails_note);
            carDetailsCreatedAt = (TextView) findViewById(R.id.carDetails_createAt);
            // initial bitmap as  null
            carDetailsImage = (ImageView) findViewById(R.id.carDetails_image);
            GetImage.getImage(carDetailsImage, car.get(0).getImageResourceId());
            //set all info on car list card
            carDetailsPrice.setText("$"+car.get(0).getPrice() + "");
            carDetailsMileage.setText(car.get(0).getMileage() + "mi");
            carDetailsYear.setText( car.get(0).getYear()+" ");
            carDetailsMake.setText(car.get(0).getMake()+" ");
            carDetailsModel.setText( car.get(0).getModel()+" ");
            carDetailsCity.setText(car.get(0).getCity() + ",");
            carDetailsState.setText(car.get(0).getState());
            carDetailsUser.setText(car.get(0).getUsername());
            carDetailsContact.setText(car.get(0).getContact());
            carDetailsNotes.setText(car.get(0).getNotes());
            carDetailsCreatedAt.setText(car.get(0).getCreatedAt());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_details, menu);
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
