package com.example.jonelezhang.cartopia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CarDetails extends AppCompatActivity {
    //JSON
    private String url;
    private String strUrl;
    static JSONArray obj = null;
    private BuyCarItem buyCar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
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
                if(json != null){
                    JSONObject finalObject = json.getJSONObject(0);
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
                }
            }catch(JSONException e){
                e.printStackTrace();
            }


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
