package com.example.wollyz.assignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by Wollyz on 25/11/2016.
 */
public class displayActivity extends Activity {

    ViewPager viewPager;
    SwipeAdapter adapter;
    int[] imgid;
    private DatabaseManager db;
    ArrayList<String> landmarks = new ArrayList<>();
    Cursor cursor;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Bundle var = getIntent().getExtras();
        String name = var.getString("LandmarkName");
        viewPager =(ViewPager)findViewById(R.id.view_pager);

        switch(name){
            case "China":
            {
                imgid = new int[]{
                        R.drawable.great_wall_of_china,
                        R.drawable.forbidden_city,
                        R.drawable.terracotta_army};
                break;
            }

            case "Italy": {
                imgid = new int[]{
                        R.drawable.colosseum,
                        R.drawable.sistine_chapel,
                };
                break;
            }

            case "America":
            {
                imgid = new int[]{
                        R.drawable.white_house
                };
                break;
            }

            case "France":{
                imgid = new int[]{
                        R.drawable.eiffel_tower,
                        R.drawable.arc_de_triomphe
                };
                break;
            }

            case "Egypt":{
                imgid = new int[]{
                        R.drawable.the_great_sphinx,
                        R.drawable.pyramids_of_giza
                };
                break;
            }


        }//end switch


        db = new DatabaseManager(this);
        db.open();
        cursor = db.getLandmarksByCountry(name);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false)
        {
            landmarks.add(cursor.getString(1));
            cursor.moveToNext();
        }
        Bundle b = getIntent().getExtras();
        //name = b.getStringArrayList("landmarks");
        adapter = new SwipeAdapter(this,imgid, landmarks);
        viewPager.setAdapter(adapter);

    }
}
