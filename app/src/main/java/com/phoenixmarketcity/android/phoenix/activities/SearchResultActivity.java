package com.phoenixmarketcity.android.phoenix.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.cards.CardUtils;
import com.phoenixmarketcity.android.phoenix.fragment.SearchResultFragment;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import org.parceler.Parcel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mujeeb on 07-Aug-15.
 */
public class SearchResultActivity extends BaseActivity {
    private static final String ARGS_LAYOUT_FILE_URL =
            SearchResultActivity.class.getName()
                    + ".layoutFileName";

    private String mPageName ="searchs";
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    SlidingTabLayout mSlidingTabs;
    private MenuItem searchItem;
    private Menu menu;
    private SearchManager searchManager;
    private SearchView searchView;
    private List<SearchResultDataList> mSearchResultsData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
        mViewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());

        // Assigning ViewPager View and setting the adapter
        mViewPager = (ViewPager) findViewById(R.id.mainPageViewPagerIdSearch);
        mViewPager.setOffscreenPageLimit(mViewPagerAdapter.getCount() - 1);

        // Assiging the Sliding Tab Layout View
        mSlidingTabs = (SlidingTabLayout) findViewById(R.id.mainPageSlidingTabsViewIdSearhch);
        mSlidingTabs.setDistributeEvenly(true);
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
        handleIntent(getIntent());
    }

    public List<SearchResultDataList> getSearchResult() {
        return mSearchResultsData;
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] mPageLayoutFiles = {"All", "Wallet", "Me", "Store"};
        private final int[] mTitles = {
                R.string.allTabTitle,
                R.string.walletTabTitle,
                R.string.meTabTitle,
                R.string.storeTabTitle};
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
                SearchResultFragment fragment = SearchResultFragment.newInstance(mPageLayoutFiles[position]);
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

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {

        WebService.getInstance().getSearchPage(mPageName, new Callback<List<SearchResultDataList>>() {
            @Override
            public void success(List<SearchResultDataList> searchResult, Response response)  {
                mSearchResultsData = searchResult;
                mViewPager.setAdapter(mViewPagerAdapter);
                // Setting the ViewPager For the SlidingTabsLayout
                mSlidingTabs.setViewPager(mViewPager);
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(),"Unable to Fetch page....."+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        this.menu = menu;
        searchItem = menu.findItem(R.id.actionbar_searchAction);
        searchView = (SearchView) searchItem.getActionView();
        MenuItem item = menu.findItem(R.id.actionbar_alertAction);
        item.setVisible(false);
        searchItem.expandActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        String query=getIntent().getStringExtra("Query");
        Toast.makeText(getApplicationContext(),"You Submited Query...."+query,Toast.LENGTH_SHORT).show();
        searchView.setQuery(query, false);
        onQuerySubmit(query);
        setupSearchView(searchItem);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //SearchView OnClose code comes here
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            // Alter action
            case R.id.actionbar_alertAction:
                return true;
            // Search action
            case R.id.actionbar_searchAction:
                searchView.setFocusable(true);
                searchView.requestFocus();
                searchView.requestFocusFromTouch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            searchView.setIconifiedByDefault(true);
            searchView.setIconified(false);
            searchView.setFocusable(true);
            searchView.requestFocus();
            searchView.requestFocusFromTouch();
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {

                if (inf.getSuggestAuthority() != null && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            searchView.setSearchableInfo(info);
        }
        searchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {
        // Code for text change comes here
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        // Code for text submission comes here
        onQuerySubmit(query);
        return true;
    }
    public boolean onClose() {
        searchView.clearFocus();
        return false;
    }
    protected boolean isAlwaysExpanded() {
        return true;
    }
    private void onQuerySubmit(String query)
    {
       // Toast.makeText(getApplicationContext(),"submitted query in SearchResults..."+query,Toast.LENGTH_SHORT).show();
    }


    @Parcel
    public static class SearchResultDataList extends CardUtils.CardConfiguration.LayoutConfiguration
    {
        @SerializedName("resulttype")
        public String mResultType;
    }
}
