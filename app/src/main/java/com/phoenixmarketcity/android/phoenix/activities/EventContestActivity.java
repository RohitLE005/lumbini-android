package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.phoenixmarketcity.android.phoenix.R;


public class EventContestActivity extends BaseActivity {
    /* setting to move back activity */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_eventcontest);
        final Button home=(Button)findViewById(R.id.FooterButtonHome);
        final Button discuss=(Button)findViewById(R.id.FooterButtonDiscuss);
        final Button Do =(Button)findViewById(R.id.FooterButtonDo);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventContestActivity.this, EventContestWinnerActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventContestActivity.this, LandingPageActivity.class);
                startActivity(i);
            }
        });
        discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventContestActivity.this, Dummypages.class);
                startActivity(i);
            }
        });
        Do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EventContestActivity.this,DoActivity.class);
                startActivity(i);
            }
        });

    }



}
