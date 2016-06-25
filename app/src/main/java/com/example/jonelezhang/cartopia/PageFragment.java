package com.example.jonelezhang.cartopia;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by Jonelezhang on 6/25/16.
 */
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
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
        View view = inflater.inflate(R.layout.info_fragment_page, container, false);
        WebView mWebView = (WebView) view;
        if(mPage == 1){
            mWebView.loadUrl("file:///android_asset/www/tab1.html");
        }else if(mPage == 2){
            mWebView.loadUrl("file:///android_asset/www/tab2.html");
        }else if(mPage == 3){
            mWebView.loadUrl("file:///android_asset/www/tab3.html");
        }
        return view;
    }


}
