package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jonelezhang on 6/18/16.
 */
//buy right sort list item
public class SortListAdapter extends BaseAdapter {
    private Context mContext;
    private String[] Title;
    private TypedArray image;

    public SortListAdapter(Context context, String[] text, TypedArray imageId){
        mContext = context;
        Title = text;
        image = imageId;
    }
    @Override
    public int getCount(){
        return Title.length;
    }
    @Override
    public Object getItem(int position){
        return null;
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_sort_list_item,null);
        }else{
            view = convertView;
        }

        ImageView i1 = (ImageView) view.findViewById(R.id.sort_icons);
        TextView title = (TextView) view.findViewById(R.id.sort_title);
        title.setText(Title[position]);
        i1.setImageResource(image.getResourceId(position, -1));
        return view;

    }
}
