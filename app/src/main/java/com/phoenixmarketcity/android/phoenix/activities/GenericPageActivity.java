package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.cards.CardUtils;
import com.phoenixmarketcity.android.phoenix.cards.CardViewCreatorCallback;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.util.PhoenixConst;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GenericPageActivity extends BaseActivity {

    // Static method to create intent to launch this activity
    public static Intent createLaunchIntent(final Context context, final String outletId) {
        final Intent intent = new Intent(context, GenericPageActivity.class);
        intent.putExtra(EXTRA_OUTLET_ID, outletId);
        return intent;
    }

    public static final String EXTRA_OUTLET_ID = GenericPageActivity.class.getName()
            + ".EXTRA.outletId";

    private ViewGroup mParentLayout;
    private ProgressBar mProgressBar;
    private int mCardCount = 0;
    private int mCardCreated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_layout);

        mParentLayout = (LinearLayout) findViewById(R.id.storeCardListPageParentLayoutId);
        mProgressBar = (ProgressBar) findViewById(R.id.cardListPageProgressBarId);

        final String outletId = getIntent().getStringExtra(EXTRA_OUTLET_ID);
        WebService.getInstance().getOutletPageLayout(outletId, new Callback<CardUtils.CardConfiguration>() {
            @Override
            public void success(CardUtils.CardConfiguration cardConfig, Response response) {
                if (!CardUtils.isCardListPageValid(cardConfig)) {
                    showEmptyPage();
                    setPageFooter(cardConfig.mPageType);
                    GenericPageActivity.this.setTitle(cardConfig.mPageTitle);
                    return;
                }
                List<CardUtils.CardConfiguration.LayoutConfiguration> layoutConfig = cardConfig.mLayoutConfiguration;
                mCardCount = layoutConfig.size();

                // Add dummy views. This is to ensure that cards
                // are added in sequence.
                // Do not merge below for loops. This is to synchronize
                // Dummy view insertion with card creation
                for (int index = 0; index < mCardCount; index++) {
                    mParentLayout.addView(new View(GenericPageActivity.this), index);
                }
                for (int index = 0; index < mCardCount; index++) {
                    CardUtils.createCard(GenericPageActivity.this, layoutConfig.get(index), new CardViewCreator(index));
                }

                setPageFooter(cardConfig.mPageType);
                GenericPageActivity.this.setTitle(cardConfig.mPageTitle);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d("Outlet " + outletId + " loading failed with error: " +
                        error.getResponse() + "/" + error.getMessage() + "/" + error.getBody());
                showEmptyPage();
            }

            private void showEmptyPage() {
                TextView errorTextView = (TextView) findViewById(R.id.error_text_view);
                errorTextView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    // Sets dynamic footer of the page
    private void setPageFooter(String pageType) {
        int footerLayout = getFooterLayout(pageType);
        ViewStub pageParentView = (ViewStub) findViewById(R.id.genericPageFooterViewId);
        pageParentView.setLayoutResource(footerLayout);
        pageParentView.inflate();
        handleFooterNavigation();
    }

    // Handles footer bar navigation
    private void handleFooterNavigation() {
        // On Collections Activity
        Button footerButton = (Button) findViewById(R.id.FooterButtonCollection);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(GenericPageActivity.this, CollectionsActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On Home Activity
        footerButton = (Button) findViewById(R.id.FooterButtonHome);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(GenericPageActivity.this, LandingPageActivity.class);
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
                Intent launchIntent = new Intent(GenericPageActivity.this, CommentsActivity.class);
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
                Intent launchIntent = new Intent(GenericPageActivity.this, DoActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

    }

    // Returns footer layout
    private int getFooterLayout(String pageType) {
        if (TextUtils.isEmpty(pageType)) return R.layout.footerbar_storedetails_catalog;

        if (pageType.equalsIgnoreCase(PhoenixConst.STORE_TYPE_FNB)) {
            return R.layout.footerbar_storedetails_fnb;
        } else if (pageType.equalsIgnoreCase(PhoenixConst.STORE_TYPE_CINEMA)) {
            return R.layout.footerbar_storedetails_cinema;
        } else if (pageType.equalsIgnoreCase(PhoenixConst.STORE_TYPE_EVENT)) {
            return R.layout.footerbar_storedetails_pre_event;
        } else if (pageType.equalsIgnoreCase(PhoenixConst.STORE_TYPE_INFO)) {
            return R.layout.footerbar_storedetails_info;
        } else if (pageType.equalsIgnoreCase(PhoenixConst.STORE_TYPE_COLLECTION)) {
            return R.layout.footerbar_store_collection;
        } else if (pageType.equalsIgnoreCase(PhoenixConst.STORE_TYPE_CATALOG)) {
            return R.layout.footerbar_storedetails_catalog;
        } else if (pageType.equalsIgnoreCase(PhoenixConst.STORE_TYPE_ECOMMERCE)) {
            return R.layout.footerbar_storedetails_ecommerce;
        } else if (pageType.equalsIgnoreCase(PhoenixConst.STORE_TYPE_SERVICE)) {
            return R.layout.footerbar_storedetails_service;
        }
        return R.layout.footerbar_storedetails_catalog;
    }

    // Creator callback
    private class CardViewCreator implements CardViewCreatorCallback {
        private int mCardIndex;
        public CardViewCreator(int cardIndex)  {
            mCardIndex = cardIndex;
        }

        @Override
        public void onSuccess(ViewGroup cardView) {
            synchronized (GenericPageActivity.this) {
                mParentLayout.removeViewAt(mCardIndex);
                mParentLayout.addView(cardView, mCardIndex);
            }
            updateProgressBar();
        }

        @Override
        public void onFailure() {
            updateProgressBar();
        }

        private void updateProgressBar() {
            mCardCreated++;
            if (mCardCreated == mCardCount) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }
}

