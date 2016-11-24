package com.example.wollyz.assignment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Wollyz on 22/11/2016.
 */
public class displayActivity extends Activity {
    Button addToList;
    int position;
    int[] imageid;
    String name;
    ImageView landmark_pic;
    TextView city;
    Cursor cursor;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        db = new DatabaseManager(this);

        landmark_pic = (ImageView)findViewById(R.id.img);
        addToList = (Button) findViewById(R.id.AddBtn);
        city = (TextView) findViewById(R.id.city);

        Bundle b = getIntent().getExtras();
        name = b.getString("Selected_Landmark");
        imageid = b.getIntArray("image");
        position = b.getInt("position");
        landmark_pic.setImageResource(imageid[position]);

        //cursor = db.getLandmarkContents(name);


        /*
        addToList.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                db.open();



            }
        });
        */


    }
}
