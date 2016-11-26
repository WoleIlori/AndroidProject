package com.example.wollyz.assignment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

/**
 * Created by Wollyz on 26/11/2016.
 */
public class modifyListActivity extends Activity {
    private String name;
    private CheckBox delete;
    private CheckBox update;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifylist);
        db = new DatabaseManager(this);
        db.open();
        Bundle l = getIntent().getExtras();
        name = l.getString("landmark");
        update =(CheckBox)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(update.isChecked()){
                    db.updateList(name);
                    finish();
                }
            }
        });

        delete =(CheckBox)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delete.isChecked()){
                    db.deleteLandmark(name);
                    finish();
                }
            }
        });

    }


}
