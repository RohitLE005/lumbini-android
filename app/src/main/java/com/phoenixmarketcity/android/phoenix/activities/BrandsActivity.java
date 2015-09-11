package com.phoenixmarketcity.android.phoenix.activities;

import android.annotation.SuppressLint;
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
import android.widget.AbsListView;
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

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BrandsActivity extends BaseActivity {

    private GridView mBrandGridView;
    private ProgressBar mProgressBar;
    private Button mFilterButton;
    private String mCurrentOutletType = null;
    private BrandGridViewAdapter mGridViewAdapter;
    private int mCurrentPageId = 1;
    private final int mPageSize = 10;
    private boolean mIsNoMoreItems = false;
    private boolean mIsLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_brands);
        mBrandGridView = (GridView) findViewById(R.id.brandGridView);
        mProgressBar = (ProgressBar) findViewById(R.id.brandsProgressBarId);
        mFilterButton = (Button) findViewById(R.id.filterButtonViewId);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrandsActivity.this, FilterPageActivity.class);
                startActivity(intent);
            }
        });

        mGridViewAdapter = new BrandGridViewAdapter(BrandsActivity.this);
        mBrandGridView.setAdapter(mGridViewAdapter);
        updateOutletList();
        handleSortButtonPress();
        handleFooterNavigation();
    }

    private void updateOutletList() {
        mIsLoading = true;
        WebService.getInstance().getOutletList(mCurrentOutletType, mCurrentPageId++, mPageSize,
                new Callback<List<BrandListItem>>() {
                    @Override
                    public void success(List<BrandListItem> brandList, Response response) {
                        mIsLoading = false;
                        mProgressBar.setVisibility(View.GONE);
                        if (brandList == null || brandList.size() == 0) {
                            showEmptyPage();
                            return;
                        }
                        mGridViewAdapter.addItems(brandList);
                        addScrollListener();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mIsLoading = false;
                        if (error != null && error.getResponse() != null
                                && error.getResponse().getStatus() == 207) {
                            mIsNoMoreItems = true;
                            mGridViewAdapter.removeDummyItems(true);
                        }
                        showEmptyPage();
                    }

                    private void showEmptyPage() {
                        if (mGridViewAdapter.getCount() == 0) {
                            TextView errorTextView = (TextView) findViewById(R.id.error_text_view);
                            errorTextView.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void addScrollListener() {
        mBrandGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                if (totalItemCount > 0 && !mIsNoMoreItems && !mIsLoading && (lastVisibleItem == totalItemCount)) {
                    updateOutletList();
                }
            }
        });
    }

    // Handle Sort button
    private void handleSortButtonPress() {
        final Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Light.ttf");
        Button sortButton = (Button) findViewById(R.id.sortButtonViewId);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BrandsActivity.this);
                builder.setTitle(R.string.sort_dialog_title_text);
                builder.setItems(R.array.sort_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // All Store
                                mProgressBar.setVisibility(View.VISIBLE);
                                mCurrentOutletType = WebService.TYPE_OF_OUTLET_ALL_STORE;
                                updateOutletList();
                                break;
                            case 1: // Popular Store
                                mProgressBar.setVisibility(View.VISIBLE);
                                mCurrentOutletType = WebService.TYPE_OF_OUTLET_POPULAR_STORE;
                                updateOutletList();
                                break;
                            case 2: // Feature Store
                                mProgressBar.setVisibility(View.VISIBLE);
                                mCurrentOutletType = WebService.TYPE_OF_OUTLET_FEATURED_STORE;
                                updateOutletList();
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

    // Handles footer bar navigation
    private void handleFooterNavigation() {
        Button footerButton = (Button) findViewById(R.id.FooterButtonBrand);
        footerButton.setSelected(true);

        // On Home Activity
        footerButton = (Button) findViewById(R.id.FooterButtonHome);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(BrandsActivity.this, LandingPageActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On Wallet Activity
        footerButton = (Button) findViewById(R.id.FooterButtonWallet);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(BrandsActivity.this, WalletActivity.class);
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
                Intent launchIntent = new Intent(BrandsActivity.this, DoActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });
    }

    public static class BrandGridViewAdapter extends BaseAdapter {
        private Context mContext;
        private List<BrandListItem> mBrands;
        private ViewHolder mHolder;

        public BrandGridViewAdapter(Context context) {
            mContext = context;
            mBrands = new ArrayList<BrandListItem>();
        }

        public void addItems(List<BrandListItem> items) {
            removeDummyItems(false);
            mBrands.addAll(items);
            addDummyItems();
            notifyDataSetChanged();
        }

        private void addDummyItems() {
            mBrands.add(BrandListItem.createDummy());
            if (mBrands.size()%2 != 0) {
                mBrands.add(BrandListItem.createDummy());
            }
        }

        public void removeDummyItems(boolean refreshViews) {
            int len = mBrands.size();
            if (len > 0 && mBrands.get(len-1).isDummy()) {
                mBrands.remove(len-1);
            }
            len = mBrands.size();
            if (len > 0 && mBrands.get(len-1).isDummy()) {
                mBrands.remove(len-1);
            }
            if (refreshViews) notifyDataSetChanged();
        }

        public int getCount() {
            return mBrands.size();
        }

        public Object getItem(int position) {
            return mBrands.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.brands_item_layout, null, false);

                mHolder = new ViewHolder();
                mHolder.mParentLayout = v.findViewById(R.id.parentLayout);
                mHolder.mItemNameView = (TextView) v.findViewById(R.id.brandItemNameTextView);
                mHolder.mItemLocationView = (TextView) v.findViewById(R.id.brandItemLocationTextView);
                mHolder.mBrandLogoView = (ImageView) v.findViewById(R.id.brandItemLogoView);
                mHolder.mProgressBar = (ProgressBar) v.findViewById(R.id.progressBarId);
                v.setTag(mHolder);
            } else {
                v = convertView;
                mHolder = (ViewHolder) v.getTag();
            }

            if (mBrands.get(position).isDummy()) {
                mHolder.mParentLayout.setVisibility(View.GONE);
                mHolder.mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mHolder.mParentLayout.setVisibility(View.VISIBLE);
                mHolder.mProgressBar.setVisibility(View.GONE);
                mHolder.mItemNameView.setText(mBrands.get(position).mBrandName);
                mHolder.mItemLocationView.setText(mBrands.get(position).mBrandLocation);
                Picasso.with(mContext).load(mBrands.get(position).mBrandLogo)
                        .placeholder(R.drawable.placeholder_1x2)
                        .error(R.drawable.placeholder_1x2)
                        .into(mHolder.mBrandLogoView);
            }
            return v;
        }

        private static class ViewHolder {
            View mParentLayout;
            TextView mItemNameView;
            TextView mItemLocationView;
            ImageView mBrandLogoView;
            ProgressBar mProgressBar;
        }
    }

    @Parcel
    public static class BrandListItem {
        @SerializedName("outlet_id")
        public String mBrandId;
        @SerializedName("outlet_name")
        public String mBrandName;
        @SerializedName("outlet_logo")
        public String mBrandLogo;
        @SerializedName("outlet_shop_number")
        public String mBrandLocation;

        public static BrandListItem createDummy() {
            BrandListItem item = new BrandListItem();
            item.mBrandId = "dummy";
            return item;
        }

        public boolean isDummy() {
            return mBrandId.equalsIgnoreCase("dummy");
        }
    }
}
