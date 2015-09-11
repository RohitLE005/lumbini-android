package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.phoenixmarketcity.android.phoenix.R;


public class Eventcontest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setContentView(R.layout.activity_eventcontest);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Eventcontest.this,Eventcontestwinner.class);
                startActivity(intent);
            }
        });
    }



}
