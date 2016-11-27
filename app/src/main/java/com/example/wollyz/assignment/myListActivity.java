package com.example.wollyz.assignment;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Wollyz on 26/11/2016.
 */
public class myListActivity extends ListActivity {
    private Cursor c;
    private String[] selLandmark;
    private DatabaseManager mydb;
    private String select;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DatabaseManager(this);
        mydb.open();
        selLandmark = mydb.getLandmarksNotVisited();
        setListAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selLandmark)
        );

    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        select = l.getItemAtPosition(position).toString();
        Intent intent = new Intent(getApplicationContext(), modifyListActivity.class);
        intent.putExtra("landmark", select);
        startActivity(intent);
    }
    //refresh list
    public void onResume()
    {
        super.onResume();
        if(getListView()!=null)
        {
            updateData();
        }
    }

    public void updateData()
    {
        selLandmark = mydb.getLandmarksNotVisited();
        setListAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selLandmark)
        );

    }
}