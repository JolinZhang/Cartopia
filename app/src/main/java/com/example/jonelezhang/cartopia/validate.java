package com.example.jonelezhang.cartopia;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by Jonelezhang on 6/9/16.
 */
public class validate extends AppCompatActivity {
    private EditText _username = (EditText) findViewById(R.id.signup_username);

    //construction
    public validate(String s){
    }

    //check the validation of username
    public boolean validation(){
        boolean valid = true;
        String username = _username.getText().toString();
       if(username.isEmpty()){
           _username.setError("can not be empty");
           valid = false;
       }else if(username.length() == 20){
           _username.setError("at most 20 characters");
           valid = false;
       }else{
           _username.setError(null);
       }

       return valid;
    }
}
