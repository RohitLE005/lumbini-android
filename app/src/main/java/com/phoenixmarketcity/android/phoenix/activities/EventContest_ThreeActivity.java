package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.phoenixmarketcity.android.phoenix.R;

public class EventContest_ThreeActivity extends BaseActivity {

    public EventDetailsActivity.EventDescriptionData mEventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_event_contest_three);

       /* final String str = mEventData.mEventDetails.mShortDesc.toString();
       final TextView mLongDesc = (TextView) findViewById(R.id.EventLongDescText);
        mLongDesc.setText(str);*/

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventContestActivity.class);
                startActivity(intent);
            }
        });
    }
}
