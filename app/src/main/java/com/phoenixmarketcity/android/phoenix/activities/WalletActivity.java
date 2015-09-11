package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WalletActivity extends BaseActivity {
    private String mPageName = "16_WalletScreen";
    private ViewGroup mParentLayout;
    private ProgressBar mProgressBar;
    private int mCardCount = 0;
    private int mCardCreated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_generic_layout);

        mParentLayout = (LinearLayout) findViewById(R.id.storeCardListPageParentLayoutId);
        mProgressBar = (ProgressBar) findViewById(R.id.cardListPageProgressBarId);
        handleFooters();

        WebService.getInstance().getLayout(mPageName, new Callback<CardUtils.CardConfiguration>() {
            @Override
            public void success(CardUtils.CardConfiguration cardConfig, Response response) {
                if (!CardUtils.isCardListPageValid(cardConfig)) {
                    showEmptyPage();
                    return;
                }
                List<CardUtils.CardConfiguration.LayoutConfiguration> layoutConfig = cardConfig.mLayoutConfiguration;
                mCardCount = layoutConfig.size();

                // Add dummy views. This is to ensure that cards
                // are added in sequence.
                // Do not merge below for loops. This is to synchronize
                // Dummy view insertion with card creation
                for (int index = 0; index < mCardCount; index++) {
                    mParentLayout.addView(new View(WalletActivity.this), index);
                }
                for (int index = 0; index < mCardCount; index++) {
                    CardUtils.createCard(WalletActivity.this, layoutConfig.get(index), new CardViewCreator(index));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d(mPageName + " page loading failed with error: " +
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

    // Handle footer buttons
    private void handleFooters() {
        ViewStub pageParentView = (ViewStub) findViewById(R.id.genericPageFooterViewId);
        pageParentView.setLayoutResource(R.layout.footerbar_homepage);
        pageParentView.inflate();

        Button footerButton = (Button) findViewById(R.id.FooterButtonWallet);
        footerButton.setSelected(true);

        // On Home Activity
        footerButton = (Button) findViewById(R.id.FooterButtonHome);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(WalletActivity.this, LandingPageActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On Wallet Button
        footerButton = (Button) findViewById(R.id.FooterButtonDo);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(WalletActivity.this, DoActivity.class);
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
                Intent launchIntent = new Intent(WalletActivity.this, BrandsActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });
    }

    // Creator callback
    private class CardViewCreator implements CardViewCreatorCallback {
        private int mCardIndex;
        public CardViewCreator(int cardIndex)  {
            mCardIndex = cardIndex;
        }

        @Override
        public void onSuccess(ViewGroup cardView) {
            synchronized (WalletActivity.this) {
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
