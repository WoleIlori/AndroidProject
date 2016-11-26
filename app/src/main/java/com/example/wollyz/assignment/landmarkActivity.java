package com.example.wollyz.assignment;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * Created by Wollyz on 16/11/2016.
 */
public class landmarkActivity extends ListActivity {
    private Cursor cursor;
    ArrayList<String> landmarks = new ArrayList<String>();
    private DatabaseManager db;
    int[] imgid;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_landmark);
        Bundle var = getIntent().getExtras();
        String name = var.getString("LandmarkName");

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
        CustomArrayAdapter adapter = new CustomArrayAdapter(this,landmarks ,imgid);
        getListView().setAdapter(adapter);

    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        //String select = l.getItemAtPosition(position).toString();
        Intent intent = new Intent(getApplicationContext(), displayActivity.class);
        //intent.putExtra("Selected_Landmark", select);
        intent.putExtra("position", position);
        intent.putExtra("landmarks", landmarks);
        intent.putExtra("image", imgid);
        startActivity(intent);

    }

}
