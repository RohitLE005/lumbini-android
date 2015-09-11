package com.phoenixmarketcity.android.phoenix.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.SearchResultActivity;
import com.phoenixmarketcity.android.phoenix.cards.CardUtils;
import com.phoenixmarketcity.android.phoenix.cards.CardViewCreatorCallback;

import java.util.List;

public class SearchResultFragment extends Fragment {

    private static final String ARGS_LAYOUT_FILE_URL =
            SearchResultFragment.class.getName()
                    + ".layoutFileName";

    SearchResultActivity.SearchResultDataList mData;
    // Use this to create new instance of this fragment
     public static SearchResultFragment newInstance(String pageName) {
        SearchResultFragment fragment = new SearchResultFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_LAYOUT_FILE_URL, pageName);
        fragment.setArguments(args);
        return fragment;
     }

    private String mPageName;
    private LinearLayout mParentLayout;
    private ProgressBar mProgressBar;
    private int resultCount;
    private int mCardCreated = 0;
    private int mCardFailureCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageName = getArguments().getString(ARGS_LAYOUT_FILE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cardlist_layout_search, container, false);
        mParentLayout = (LinearLayout) rootView.findViewById(R.id.searchpage_parent_layout);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.searchpage_progressbar);
        createSearchLayout();
        return rootView;
    }

    private void createSearchLayout() {
        List<SearchResultActivity.SearchResultDataList> searchResult = ((SearchResultActivity) getActivity()).getSearchResult();
        resultCount = 0;
        int totalItemCount = searchResult.size();
        for (int index = 0; index < totalItemCount; index++) {
            if (isCardRelevant(searchResult.get(index))) {
                mParentLayout.addView(new View(getActivity()), resultCount++);
            }
        }
        for (int index = 0, currentItemCount=0; index < totalItemCount; index++) {
            if (isCardRelevant(searchResult.get(index))) {
                CardUtils.createCard(getActivity(), searchResult.get(index), new CardViewCreator(currentItemCount++));
            }
        }
    }

    private boolean isCardRelevant(SearchResultActivity.SearchResultDataList cardData) {
        if (mPageName.equalsIgnoreCase("all")) return true;
        return cardData.mResultType.toLowerCase().contains(mPageName.toLowerCase());
    }

    // Creator callback
    private class CardViewCreator implements CardViewCreatorCallback {
        private int mCardIndex;
        public CardViewCreator(int cardIndex)  {
            mCardIndex = cardIndex;
        }

        @Override
        public void onSuccess(ViewGroup cardView) {
            synchronized (SearchResultFragment.this) {
                int index = mCardIndex - mCardFailureCount;
                mParentLayout.removeViewAt(index);
                mParentLayout.addView(cardView, index);
            }
            updateProgressBar();
        }

        @Override
        public void onFailure() {
            mCardFailureCount++;
            synchronized (SearchResultFragment.this) {
                mParentLayout.removeViewAt(mCardIndex);
            }
            updateProgressBar();
        }

        private void updateProgressBar() {
            mCardCreated++;
            if (mCardCreated == resultCount) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }
}


