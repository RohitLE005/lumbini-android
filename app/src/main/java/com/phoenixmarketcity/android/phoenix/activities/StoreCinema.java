package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;


public class StoreCinema extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footerbar_storedetails_cinema);
        final Button home=(Button)findViewById(R.id.FooterButtonHome);
        final LinearLayout store=(LinearLayout)findViewById(R.id.zara_footer);
        final Button Do =(Button)findViewById(R.id.FooterButtonDo);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        //geting the first text


        Button CommentButton=(Button)findViewById(R.id.FooterButtonComments);
        CommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LandingPageActivity.class);
                startActivity(i);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Dummypages.class);
                startActivity(i);
            }
        });
        Do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DoActivity.class);
                startActivity(i);
            }
        });
    }

}