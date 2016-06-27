package com.example.jonelezhang.cartopia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    static JSONArray obj = null;
    private BuyCarItem buyCar;
    private ArrayList<BuyCarItem> car;
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
    private static final String TAG_CREATEDAT = "created_at";
    // widgets on car details
    private  TextView carDetailsPrice;
    private TextView carDetailsMileage;
    private TextView carDetailsYear;
    private TextView carDetailsMake;
    private TextView carDetailsModel;
    private  TextView carDetailsCity;
    private TextView carDetailsState;
    private TextView carDetailsContact;
    private TextView carDetailsNotes;
    private TextView carDetailsCreatedAt;
    private  ImageView carDetailsImage;

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
    }


    //use AsyncTask to run JsonParse on a different thread
    private class CarDetailsJSONParse extends AsyncTask<String, String, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            JsonParser jParser = new JsonParser();
            strUrl = jParser.getJsonFromUrl(params[0]);
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
            //get car id value
            try {
                // Storing  JSON item in a Variable, define an ArrayList carItems to store all cars' info.
                car = new ArrayList<>();
                if(json != null){
                    for(int i=0; i<json.length(); i++) {
                        JSONObject finalObject = json.getJSONObject(i);
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
                        buyCar.setCreatedAt(finalObject.getString(TAG_CREATEDAT));
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
            carDetailsContact = (TextView) findViewById(R.id.carDetails_contact);
            carDetailsNotes = (TextView) findViewById(R.id.carDetails_note);
            carDetailsCreatedAt = (TextView) findViewById(R.id.carDetails_createAt);
            // initial bitmap as  null
            carDetailsImage = (ImageView) findViewById(R.id.carDetails_image);
            GetImage.getImage(carDetailsImage, car.get(0));
            //set all info on car list card
            carDetailsPrice.setText("$"+car.get(0).getPrice() + "");
            carDetailsMileage.setText(car.get(0).getMileage() + "mi");
            carDetailsYear.setText( car.get(0).getYear()+" ");
            carDetailsMake.setText(car.get(0).getMake()+" ");
            carDetailsModel.setText( car.get(0).getModel()+" ");
            carDetailsCity.setText(car.get(0).getCity()+",");
            carDetailsState.setText(car.get(0).getState());
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
