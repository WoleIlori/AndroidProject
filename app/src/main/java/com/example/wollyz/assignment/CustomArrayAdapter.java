package com.example.wollyz.assignment;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Wollyz on 18/11/2016.
 */


public class CustomArrayAdapter extends ArrayAdapter<String> {
    private int[] imgid;
    private String[] landmarks;
    private Context context;

    public CustomArrayAdapter(Context context, String[] name, int[] imageId )
    {
        super(context, R.layout.landmark_row, name);
        this.context = context;
        landmarks = name;
        imgid = imageId;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.landmark_row, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView name = (TextView) rowView.findViewById(R.id.landmarkname);
        name.setText(landmarks[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;




    }



}
