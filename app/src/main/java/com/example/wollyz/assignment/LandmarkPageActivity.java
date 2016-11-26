package com.example.wollyz.assignment;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Wollyz on 25/11/2016.
 */
public class LandmarkPageActivity extends Activity {
    //Button addBtn;
    ImageView landmark_pic;
    //TextView title;
    Cursor cursor;
    private DatabaseManager db;
    int position = 0;
    String name;
    int[] imageid = new int[]{
            R.drawable.great_wall_of_china,
            R.drawable.forbidden_city,
            R.drawable.terracotta_army,
            R.drawable.colosseum,
            R.drawable.sistine_chapel,
            R.drawable.white_house,
            R.drawable.eiffel_tower,
            R.drawable.arc_de_triomphe,
            R.drawable.the_great_sphinx,
            R.drawable.pyramids_of_giza
    };
    String[] allLandmarks = new String[imageid.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_page);

        db = new DatabaseManager(this);
        db.open();

        landmark_pic = (ImageView)findViewById(R.id.image_view);
        //addBtn = (Button)findViewById(R.id.Button);
        //title = (TextView)findViewById(R.id.text);
        Bundle b = getIntent().getExtras();
        name = b.getString("landmark");
        /*
        name = db.getLandmarkByName(b.getString("landmark"));
        title.setText(name);
        */
        cursor = db.getAllLandmarks();
        cursor.moveToFirst();
        for(int i = 0; i<allLandmarks.length; i++)
        {
            while(!cursor.isAfterLast())
            {
                allLandmarks[i] =  cursor.getString(1);
                cursor.moveToNext();
            }
        }


    }
}
