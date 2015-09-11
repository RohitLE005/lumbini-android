package com.phoenixmarketcity.android.phoenix.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;
import com.squareup.picasso.Picasso;

import org.parceler.Parcel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mujeeb on 09-Sep-15.
 */
public class CollectionsActivity extends BaseActivity {

    private GridView mCollectionsGridView;
    private ProgressBar mProgressBar;
    private Button mFilterButton;
    private String mPageName = "collections";
    private CollectionsGridViewAdapter mAdapter;
    private List<CollectionListItems> mDataItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_collection_products);
        mCollectionsGridView = (GridView) findViewById(R.id.collectionsGridView);
        mProgressBar = (ProgressBar) findViewById(R.id.collections_ProgressBarId);
        mFilterButton = (Button) findViewById(R.id.collections_filterButtonViewId);

        collectionsUpdateList();
        handleFooterNavigation();
        handleSortButtonPress();
        handleFilterButtonPress();
    }
    //Products collection update list
    private void collectionsUpdateList()
    {
        WebService.getInstance().getCollectionsList(mPageName, new Callback<List<CollectionListItems>>() {
            @Override
            public void success(List<CollectionListItems> categories, Response response) {
                // mDataItems = categories;
                mProgressBar.setVisibility(View.GONE);
                mAdapter = new CollectionsGridViewAdapter(CollectionsActivity.this, categories);
                mCollectionsGridView.setAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                TextView errorTextView = (TextView) findViewById(R.id.collections_error_text_view);
                errorTextView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
    // Handle Sort button
    private void handleSortButtonPress() {
        final Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Light.ttf");
        Button sortButton = (Button) findViewById(R.id.collections_sortButtonViewId);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionsActivity.this);
                builder.setTitle(R.string.sort_dialog_title_text);
                builder.setItems(R.array.sort_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // All Store
                                collectionsUpdateList();
                                break;
                            case 1: // Popular Store
                                collectionsUpdateList();
                                break;
                            case 2: // Feature Store
                                collectionsUpdateList();
                                break;
                        }
                    }
                });

                Dialog d = builder.show();
                int textViewId = d.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
                TextView tv = (TextView) d.findViewById(textViewId);
                tv.setTypeface(face);
                tv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                tv.setGravity(Gravity.CENTER);
                int dividerId = d.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
                View divider = d.findViewById(dividerId);
                divider.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
            }
        });
    }

    // Handle Filter button
    private void handleFilterButtonPress() {
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectionsActivity.this, FilterPageActivity.class);
                startActivity(intent);
            }
        });
    }
    // Handles footer bar navigation
    private void handleFooterNavigation() {
        Button footerButton = (Button) findViewById(R.id.FooterButtonCollection);
        footerButton.setSelected(true);

        // On Home Activity
        footerButton = (Button) findViewById(R.id.FooterButtonHome);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(CollectionsActivity.this, LandingPageActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On comments Activity
        footerButton = (Button) findViewById(R.id.FooterButtonComments);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(CollectionsActivity.this, CommentsActivity.class);
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
                Intent launchIntent = new Intent(CollectionsActivity.this, DoActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });
    }

    public static class CollectionsGridViewAdapter extends BaseAdapter {

        private Context mContext;
        private List<CollectionListItems> mItems;
        private ViewHolder mHolder;

        CollectionsGridViewAdapter(Context context, List<CollectionListItems> items) {
            mContext = context;
            mItems = items;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mItems.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.collections_item_layout, null);
                mHolder = new ViewHolder();
                mHolder.mProductImageView = (ImageView) convertView.findViewById(R.id.collectionItemLogoView);
                mHolder.mProductName = (TextView) convertView.findViewById(R.id.collectionItemNameTextView);
                mHolder.mProductPrice = (TextView) convertView.findViewById(R.id.collectionItemLocationTextView);

                Picasso.with(mContext).load(mItems.get(position).mProductLogo).placeholder(R.drawable.placeholder_1x2).error(R.drawable.placeholder_1x2).into(mHolder.mProductImageView);
                mHolder.mProductName.setText(mItems.get(position).mProductName);
                mHolder.mProductPrice.setText(mItems.get(position).mProductPrice);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        private static class ViewHolder {
            TextView mProductName, mProductPrice;
            ImageView mProductImageView;
        }
    }

    @Parcel
    public static class CollectionListItems {
        @SerializedName("product_image")
        public String mProductLogo;
        @SerializedName("product_name")
        public String mProductName;
        @SerializedName("product_price")
        public String mProductPrice;
    }
}
