package com.phoenixmarketcity.android.phoenix.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.phoenixmarketcity.android.phoenix.R;


public class Eventcontestwinner extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_eventcontestwinner);
        ImageView rimage=(ImageView)findViewById(R.id.rimage);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.winner);
        rimage.setImageBitmap(icon);
    }


}
