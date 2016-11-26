package com.example.wollyz.assignment;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Wollyz on 26/11/2016.
 */
public class myListActivity extends ListActivity {
    Cursor c;
    String[] selLandmark;
    ArrayAdapter adapter;

    private DatabaseManager mydb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DatabaseManager(this);
        mydb.open();

        selLandmark = mydb.getListLandmark();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selLandmark);
        setListAdapter(adapter);

    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String select = l.getItemAtPosition(position).toString();
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
        selLandmark = mydb.getListLandmark();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selLandmark);
        setListAdapter(adapter);
    }
}