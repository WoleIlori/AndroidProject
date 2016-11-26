package com.example.wollyz.assignment;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Wollyz on 26/11/2016.
 */
public class myListActivity extends ListActivity {
    Cursor c;
    String[] selLandmark;

    private DatabaseManager mydb;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mydb = new DatabaseManager(this);
        mydb.open();

        selLandmark = mydb.getListLandmark();

        setListAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selLandmark)
        );
    }

}
