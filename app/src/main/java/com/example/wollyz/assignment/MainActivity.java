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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import static java.security.AccessController.getContext;
//this is the home page of the app
public class MainActivity extends AppCompatActivity {

    Button landmarkBtn;
    Button countryBtn;
    Button myListBtn;

    //TextView search;
    EditText searchTxt;
    String input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchTxt = (EditText)findViewById(R.id.inputtext);
        //thi
        landmarkBtn = (Button)findViewById(R.id.btn);
        landmarkBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                input = searchTxt.getText().toString();
                Intent intent = new Intent(getApplicationContext(),LandmarkPageActivity.class);
                intent.putExtra("landmark", input);
                startActivity(intent);
            }
        });

        countryBtn = (Button)findViewById(R.id.CountryBtn);
        countryBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent countryintent = new Intent(getApplicationContext(),countryActivity.class);
                startActivity(countryintent);
            }
        });

        myListBtn = (Button)findViewById(R.id.ListBtn);
        myListBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent listintent = new Intent(getApplicationContext(),myListActivity.class);
                startActivity(listintent);
            }
        });

        Button visitBtn = (Button)findViewById(R.id.viewBtn);
        visitBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent listintent = new Intent(getApplicationContext(),landmarkActivity.class);
                startActivity(listintent);
            }
        });


    }
}
