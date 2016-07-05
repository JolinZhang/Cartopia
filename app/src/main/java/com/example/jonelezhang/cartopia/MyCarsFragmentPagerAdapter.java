package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Jonelezhang on 7/5/16.
 */
public class MyCarsFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT ;
    private Context context;
    private ArrayList<BuyCarItem> Items;

    public MyCarsFragmentPagerAdapter(FragmentManager fm , Context context, int count, ArrayList<BuyCarItem> CarsItems) {
        super(fm);
        this.context = context;
        this.PAGE_COUNT = count;
        this.Items = CarsItems;
    }

    @Override
    public Fragment getItem(int position) {
        return MyCarsPageFragment.newInstance(position,Items.get(position));
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
