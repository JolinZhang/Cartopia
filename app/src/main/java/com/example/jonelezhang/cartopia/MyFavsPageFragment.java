package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Jonelezhang on 7/1/16.
 */
public class MyFavsPageFragment extends Fragment {
    public static final String ARG_PAGE = "page";
    public static final String ARG_IMAGE = "image";
    public static final String ARG_CAR_ID = "car_id";
    private int mPageNumber;
    private String mPageImage;
    private int mPageCarId;
    Context mContext;
    // favs fragement image view
    private ImageView tv;


    public static MyFavsPageFragment create(int pageNumber, BuyCarItem item) {
        MyFavsPageFragment fragment = new MyFavsPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putString(ARG_IMAGE,item.getImageResourceId());
        args.putInt(ARG_CAR_ID, item.getCar_id());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mPageImage = getArguments().getString(ARG_IMAGE);
        mPageCarId = getArguments().getInt(ARG_CAR_ID);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.my_favs_fragment_page, container, false);
        // Set the title view to show the page number.
        tv = (ImageView) rootView.findViewById(R.id.favs_image);
        //GetImage.getImage(tv, mPageImage);
        Glide
                .with(getActivity().getApplicationContext())
                .load("http://cartopia.club/assets/user_car/" + mPageImage)
                .crossFade()
                .into(tv);

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
