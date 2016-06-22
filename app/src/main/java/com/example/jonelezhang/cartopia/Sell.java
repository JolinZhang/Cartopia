package com.example.jonelezhang.cartopia;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Sell extends ToolbarConfiguringActivity {
    //wiget on actionbar
    private TextView toolbar_title;
    private TextView toolbar_filter;
    //initial spinner
    private Spinner year;
    private Spinner state;
    //

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

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
