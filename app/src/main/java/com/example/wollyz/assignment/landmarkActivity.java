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
    private DatabaseManager db;
    String[] visitedLandmark;

    private int[] imageid = new int[]{//the r.drawable of all the landmarks
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
    int[] visitedImage;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        db = new DatabaseManager(this);
        db.open();

        visitedLandmark = db.getLandmarksVisited();

        //put all landmarks in array
        cursor = db.getAllLandmarks();
        cursor.moveToFirst();
        int i=0;
        while(!cursor.isAfterLast())
        {
            allLandmarks[i] =  cursor.getString(1);
            cursor.moveToNext();
            i++;
        }

        // Store the r.drawable for visited landmarks
        for(i = 0; i <visitedLandmark.length; i++)
        {
           for(int j=0; j<allLandmarks.length; j++)
           {
               if(visitedLandmark[i].matches(allLandmarks[j]))
               {
                   visitedImage[i]=imageid[j];
               }
           }
        }

        //display the image beside the name of landmarks visited
        CustomArrayAdapter adapter = new CustomArrayAdapter(this,visitedLandmark ,visitedImage);
        getListView().setAdapter(adapter);
    }


}
