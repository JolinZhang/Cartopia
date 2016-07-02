package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jonelezhang on 7/1/16.
 */
public class MyFavsPageFragment extends Fragment {
    public static final String ARG_PAGE = "page";
    private int mPageNumber;

    public static MyFavsPageFragment create(int pageNumber) {
        MyFavsPageFragment fragment = new MyFavsPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public MyFavsPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.my_favs_fragment_page, container, false);

        // Set the title view to show the page number.
        TextView tv = (TextView) rootView.findViewById(R.id.favs_text);
        tv.setText((mPageNumber + 1) + "");

        new FavsJSONParse().execute();

        return rootView;
    }

    private class FavsJSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
//            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
//            String user_id = sharedpreferences.getString("Current_User","");
//


            return null;
        }
    }



}
