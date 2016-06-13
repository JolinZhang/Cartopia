package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Jonelezhang on 6/13/16.
 */
public class DrawerListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<NavDrawerItem> mNavItems;

    public DrawerListAdapter(Context context, ArrayList<NavDrawerItem> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount(){
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position){
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_list_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);

        titleView.setText( mNavItems.get(position).mTitle );

        return view;
    }
}
