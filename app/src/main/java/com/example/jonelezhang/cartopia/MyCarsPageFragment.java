package com.example.jonelezhang.cartopia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by Jonelezhang on 7/5/16.
 */
public class MyCarsPageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static MyCarsPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MyCarsPageFragment fragment = new MyCarsPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_cars_fragment_page, container, false);
        TextView tx = (TextView) view.findViewById(R.id.text1);
        if(mPage == 1){
            tx.setText("shane");
        }else if(mPage == 2){
            tx.setText("Qi");
        }else if(mPage == 3){
            tx.setText("good");
        }
        return view;
    }




}
