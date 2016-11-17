package com.example.wollyz.assignment;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Wollyz on 16/11/2016.
 */
public class landmarkActivity extends ListActivity {
    private Cursor cursor;
    private DatabaseManager db;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);
        Bundle landmark = getIntent().getExtras();
        String name = landmark.getString("landmark_name");
        db = new DatabaseManager(this);
        db.open();
        cursor = db.getLandmarksByCountry(name);

        String[] from = new String[]{DatabaseManager.COLUMN_LANDMARKNAME};
        int[] to = new int[]{android.R.id.text1};
        ListAdapter adpter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,cursor,from,to,0);
        getListView().setAdapter(adpter);
    }
}
