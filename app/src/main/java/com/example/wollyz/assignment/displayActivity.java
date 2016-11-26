package com.example.wollyz.assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by Wollyz on 25/11/2016.
 */
public class displayActivity extends Activity {

    ViewPager viewPager;
    SwipeAdapter adapter;
    int[] imageid;
    ArrayList<String> name = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Bundle b = getIntent().getExtras();
        name = b.getStringArrayList("landmarks");
        imageid = b.getIntArray("image");
        viewPager =(ViewPager)findViewById(R.id.view_pager);
        adapter = new SwipeAdapter(this,imageid, name);
        viewPager.setAdapter(adapter);

    }
}
