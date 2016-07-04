package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Jonelezhang on 6/30/16.
 */
public class MyFavsFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private int PAGE_COUNT;
    private Context context;
    private ArrayList<BuyCarItem> Items;

    //constructor
    public MyFavsFragmentPagerAdapter(FragmentManager fm, Context context ,int count, ArrayList<BuyCarItem> FavsItems){
        super(fm);
        this.context = context;
        this.PAGE_COUNT = count;
        this.Items = FavsItems;
    }

    @Override
    public Fragment getItem(int position) {
        return MyFavsPageFragment.create(position,Items.get(position));
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
