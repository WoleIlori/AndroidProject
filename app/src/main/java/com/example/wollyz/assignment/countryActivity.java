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
 * Created by Wollyz on 18/11/2016.
 */
public class countryActivity extends ListActivity {
    private Cursor cursor;
    private DatabaseManager mydb;
    ArrayList<String> countries = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_country);
        mydb = new DatabaseManager(this);
        mydb.open();

        //
        cursor = mydb.getAllCountry();

        //
        cursor.moveToFirst();


        //
        while (cursor.isAfterLast() == false)
        {
            countries.add(cursor.getString(1));
            cursor.moveToNext();
        }

        setListAdapter(
                new ArrayAdapter<String>(this, R.layout.country_row, R.id.countryname, countries)
        );
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        String select = l.getItemAtPosition(position).toString();
        Intent intent = new Intent(getApplicationContext(), landmarkActivity.class);
        intent.putExtra("LandmarkName", select);
        startActivity(intent);
    }


}
