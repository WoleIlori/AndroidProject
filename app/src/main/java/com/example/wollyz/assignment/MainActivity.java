package com.example.wollyz.assignment;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.IOException;

import static java.security.AccessController.getContext;

public class MainActivity extends ListActivity {

    private Cursor cursor;
    private DatabaseManager mydb;
    ListView lv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //access the database
        mydb = new DatabaseManager(this);
        mydb.open();

        cursor = mydb.getAllCountry();


        String[] from = new String[]{DatabaseManager.COLUMN_COUNTRYNAME};

        int[] to = new int[]{R.id.countryname};

        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.country_row,cursor,from,to,0);//R.layout.landmark_row
        lv = getListView();
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv = (TextView)findViewById(R.id.countryname);
                String country_val = tv.getText().toString();
                //send landmark selected landmark to new  activity
                Intent intent = new Intent(getApplicationContext(),landmarkActivity.class);
                intent.putExtra("LandmarkName",country_val);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        cursor.close();
    }




}
