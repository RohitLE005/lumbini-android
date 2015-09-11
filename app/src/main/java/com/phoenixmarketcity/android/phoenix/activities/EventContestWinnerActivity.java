package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.phoenixmarketcity.android.phoenix.R;


public class EventContestWinnerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventcontestwinner);
        final Button home=(Button)findViewById(R.id.FooterButtonHome);
        final Button discuss=(Button)findViewById(R.id.FooterButtonDiscuss);
        final Button Do =(Button)findViewById(R.id.FooterButtonDo);
        ImageView rimage=(ImageView)findViewById(R.id.rimage);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.winner);
        rimage.setImageBitmap(icon);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventContestWinnerActivity.this, LandingPageActivity.class);
                startActivity(i);
            }
        });
        discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventContestWinnerActivity.this, Dummypages.class);
                startActivity(i);
            }
        });
        Do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EventContestWinnerActivity.this,DoActivity.class);
                startActivity(i);
            }
        });

    }


}
