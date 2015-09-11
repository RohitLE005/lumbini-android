package com.phoenixmarketcity.android.phoenix.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class LandingPageFragment extends Fragment {
    private static final String ARGS_LAYOUT_FILE_URL =
            LandingPageFragment.class.getName()
            + ".layoutFileName";

    // Use this to create new instance of this fragment
    public static LandingPageFragment newInstance(String pageName) {
        LandingPageFragment fragment = new LandingPageFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_LAYOUT_FILE_URL, pageName);
        fragment.setArguments(args);

        return fragment;
    }

    private String mPageName;
    private LinearLayout mParentLayout;
    private ProgressBar mProgressBar;
    private int mCardCount;
    private int mCardCreated = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageName = getArguments().getString(ARGS_LAYOUT_FILE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.cardlist_layout, container, false);
        mParentLayout = (LinearLayout) rootView.findViewById(R.id.homepage_parent_layout);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.homepage_progressbar);

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
                    mParentLayout.addView(new View(getActivity()), index);
                }
                for (int index = 0; index < mCardCount; index++) {
                    CardUtils.createCard(getActivity(), layoutConfig.get(index), new CardViewCreator(index));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d(mPageName + " page loading failed with error: " +
                        error.getResponse() + "/" + error.getMessage() + "/" + error.getBody());
                showEmptyPage();
            }

            private void showEmptyPage() {
                TextView errorTextView = (TextView) rootView.findViewById(R.id.error_text_view);
                errorTextView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    // Creator callback
    private class CardViewCreator implements CardViewCreatorCallback {
        private int mCardIndex;
        public CardViewCreator(int cardIndex)  {
            mCardIndex = cardIndex;
        }

        @Override
        public void onSuccess(ViewGroup cardView) {
            synchronized (LandingPageFragment.this) {
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


