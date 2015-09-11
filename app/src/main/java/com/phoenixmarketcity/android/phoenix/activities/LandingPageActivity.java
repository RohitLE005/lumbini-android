package com.phoenixmarketcity.android.phoenix.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kontakt.sdk.android.ble.util.BluetoothUtils;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.fragment.LandingPageFragment;

public class LandingPageActivity extends BaseActivity {
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    SlidingTabLayout mSlidingTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mViewPagerAdapter =  new ViewPagerAdapter(this, getSupportFragmentManager());

        // Assigning ViewPager View and setting the adapter
        mViewPager = (ViewPager) findViewById(R.id.mainPageViewPagerId);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(mViewPagerAdapter.getCount()-1);

        // Assiging the Sliding Tab Layout View
        mSlidingTabs = (SlidingTabLayout) findViewById(R.id.mainPageSlidingTabsViewId);
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mSlidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.black);
            }

            @Override
            public int getDividerColor(int position) {
                return android.R.color.white;
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        mSlidingTabs.setViewPager(mViewPager);
        handleFooterNavigation();
        notifyForBluetooth();
    }

    // Handles footer bar navigation
    private void handleFooterNavigation() {
        Button footerButton = (Button) findViewById(R.id.FooterButtonHome);
        footerButton.setSelected(true);

        // On Brands Button
        footerButton = (Button) findViewById(R.id.FooterButtonBrand);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(LandingPageActivity.this, BrandsActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On Wallet Button
        footerButton = (Button) findViewById(R.id.FooterButtonWallet);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(LandingPageActivity.this, WalletActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On Do Activity
        footerButton = (Button) findViewById(R.id.FooterButtonDo);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(LandingPageActivity.this, DoActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });
    }

    // Gives notification for bluetooth
    private void notifyForBluetooth() {
        if (BluetoothUtils.isBluetoothLeSupported(getApplicationContext()) && !BluetoothUtils.isBluetoothEnabled()) {
            Toast.makeText(this, R.string.bluetooth_enable_message, Toast.LENGTH_LONG).show();
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] mPageLayoutFiles = {"home", "shop", "dine", "movie", "event", "offer", "more"};
        private final int[] mTitles = {
                R.string.homeTabTitle,
                R.string.shopTabTitle,
                R.string.dineTabTitle,
                R.string.moviesTabTitle,
                R.string.eventsTabTitle,
                R.string.OfferTabTitle,
                R.string.MoreTabTitle};
        private Context mContext;

        // Constructor
        public ViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        // Returns page fragment for a given index
        @Override
        public Fragment getItem(int position) {
            if (position < mPageLayoutFiles.length) {
                LandingPageFragment fragment = LandingPageFragment.newInstance(mPageLayoutFiles[position]);
                return fragment;
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(mTitles[position]);
        }

        @Override
        public int getCount() {
            return mPageLayoutFiles.length;
        }
    }
}
