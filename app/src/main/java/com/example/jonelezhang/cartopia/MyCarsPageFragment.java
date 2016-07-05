package com.example.jonelezhang.cartopia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jonelezhang on 7/5/16.
 */
public class MyCarsPageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_IMAGE = "image";
    public static final String ARG_CAR_ID = "car_id";
    private int mPage;
    private String mPageImage;
    private int mPageCarId;
    // favs fragement image view
    private ImageView tv;
    private TextView tx;

    public static MyCarsPageFragment newInstance(int page, BuyCarItem item) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_IMAGE,item.getImageResourceId());
        args.putInt(ARG_CAR_ID, item.getCar_id());
        MyCarsPageFragment fragment = new MyCarsPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        mPageImage = getArguments().getString(ARG_IMAGE);
        mPageCarId = getArguments().getInt(ARG_CAR_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.my_cars_fragment_page, container, false);

        tx = (TextView) rootView.findViewById(R.id.text1);
        tx.setText(mPage+"");
        // Set the title view to show the page number.
        tv = (ImageView) rootView.findViewById(R.id.mycars_image);
        GetImage.getImage(tv, mPageImage);

        // image click issue and show car details
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CarDetails.class);
                i.putExtra("id", mPageCarId + "");
                startActivity(i);
            }
        });

        return rootView;
    }




}
