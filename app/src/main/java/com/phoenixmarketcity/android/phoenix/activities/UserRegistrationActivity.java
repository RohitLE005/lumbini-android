package com.phoenixmarketcity.android.phoenix.activities;

import android.os.Bundle;

import com.phoenixmarketcity.android.phoenix.R;


public class UserRegistrationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_user_registration);
    }


}
