package com.phoenixmarketcity.android.phoenix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;

import java.util.ArrayList;
import java.util.List;

public class DoActivity extends BaseActivity {
    private ListView mListView;
    private DoAdapterClass mAdapter;
    private List<DoAdapterClass.RowItem> rowItems;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.do_action_menu);
        mListView = (ListView) findViewById(R.id.doListView);

        String[] menutitles = getResources().getStringArray(R.array.do_items_Textlist);
        int[] menuimagess={R.drawable.check_in,
                R.drawable.parking,
                R.drawable.click_a_photo,
                R.drawable.find_a_place,
                R.drawable.scan_a_code,
                R.drawable.augment_reality,
                R.drawable.wheelchair,
                R.drawable.shopping_assistance,
                R.drawable.book_a_party,
                R.drawable.one_touch_assistance,
                R.drawable.book_a_cab};
        rowItems = new ArrayList<>();
        for (int i = 0; i < menutitles.length; i++) {
            DoAdapterClass.RowItem items = new DoAdapterClass.RowItem(menutitles[i],menuimagess[i]);
            rowItems.add(items);
        }
        mAdapter = new DoAdapterClass(DoActivity.this, rowItems);
        mListView.setAdapter(mAdapter);

       handleFooterButton();
    }

    // Handle footer buttons
    private void handleFooterButton() {
        Button footerButton = (Button) findViewById(R.id.FooterButtonDo);
        footerButton.setSelected(true);

        // On Home Activity
        footerButton = (Button) findViewById(R.id.FooterButtonHome);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(DoActivity.this, LandingPageActivity.class);
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
                Intent launchIntent = new Intent(DoActivity.this, WalletActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On Brand Button
        footerButton = (Button) findViewById(R.id.FooterButtonBrand);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(DoActivity.this, BrandsActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });
    }


    public static class DoAdapterClass extends BaseAdapter {
        private Context mContext;
        private List<RowItem> mRowItem;

        DoAdapterClass(Context context, List<RowItem> rowItem) {
            this.mContext = context;
            this.mRowItem = rowItem;
        }

        @Override
        public int getCount() {
            return mRowItem.size();
        }

        @Override
        public Object getItem(int position) {
            return mRowItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mRowItem.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.do_action_items, null);
            }

            TextView txtTitle = (TextView) convertView.findViewById(R.id.doTitleTextViewId);
            txtTitle.setText(mRowItem.get(position).getTitle());
            ImageView imgView=(ImageView)convertView.findViewById(R.id.doTitleImageViewId);
            imgView.setImageResource(mRowItem.get(position).getImage());
            return convertView;
        }

        public static class RowItem {
            public int mImage;
            public String title;

            public RowItem(String title,int image) {
                this.title = title;
                this.mImage=image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
            public int getImage() {
                return mImage;
            }

            public void setImage(int image) {
                this.mImage = image;
            }

        }
    }

}