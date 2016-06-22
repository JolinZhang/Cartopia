package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;

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
    //set string value for each widget
    private  String _year;
    private String _make;
    private String _model;
    private String _mileage;
    private String _price;
    private String _state;
    private String _city;
    private String _contact;
    private String _note;


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

        _make = make.getText().toString();
        _model = model.getText().toString();
        _mileage = mileage.getText().toString();
        _price = price.getText().toString();
        _city = city.getText().toString();
        _contact = contact.getText().toString();
        _note = note.getText().toString();

        //get picture name into string  when click choose picture button
        choose_photo = (Button) findViewById(R.id.sell_choose_photo);
        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });

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
