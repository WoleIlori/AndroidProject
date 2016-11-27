package com.example.wollyz.assignment;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Wollyz on 25/11/2016.
 */
public class LandmarkPageActivity extends Activity {
    //TextView title;
    private Cursor cursor;
    private DatabaseManager db;
    private String name;

    private int[] imageid = new int[]{
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

        ImageView landmark_pic = (ImageView)findViewById(R.id.image_view);
        Button addBtn = (Button)findViewById(R.id.Button);
        Bundle b = getIntent().getExtras();
        name= b.getString("landmark");

        //getting the image of the Landmark searched
        cursor = db.getAllLandmarks();
        cursor.moveToFirst();
        int i=0;
        while(!cursor.isAfterLast())
        {
            allLandmarks[i] =  cursor.getString(1);
            cursor.moveToNext();
            i++;
        }

        for(i = 0; i <allLandmarks.length; i++)
        {
            if(name.matches(allLandmarks[i]))
            {
                landmark_pic.setImageResource(imageid[i]);
            }
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addLandmarkToList(name);
                finish();

            }
        });


    }
}
