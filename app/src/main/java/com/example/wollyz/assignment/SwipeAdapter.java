package com.example.wollyz.assignment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Wollyz on 25/11/2016.
 */
public class SwipeAdapter extends PagerAdapter {

    //pass resourceid of the image
    private int[] image_resource;
    private Context ctx;
    private LayoutInflater layoutInflater;

    public SwipeAdapter(Context ctx, int[] imgid){
        this.ctx = ctx;
        image_resource = imgid;
    }
    @Override
    public int getCount() {
        //returnnumber of images i.e array.length
        return image_resource.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view =  layoutInflater.inflate(R.layout.swipe_layout, container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.image);
        //6Button button = (Button)item_view.findViewById(R.id.AddBtn);
        imageView.setImageResource(image_resource[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    //destroy slides
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
