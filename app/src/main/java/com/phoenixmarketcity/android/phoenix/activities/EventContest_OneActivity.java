package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.phoenixmarketcity.android.phoenix.R;

public class EventContest_OneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_event_contest1);

        Button btn = (Button) findViewById(R.id.btn_PostSelfie);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventContest_OneActivity.this, EventContest_ThreeActivity.class);
                startActivity(intent);
            }
        });
    }


}
