package com.phoenixmarketcity.android.phoenix.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.util.Preferences;

import java.util.List;

/**
 * Base class for all activity in the project.
 * Key functionalities:
 * 1. Implements action bar
 * 2. Lock orientation to portrait
 */

public class BaseActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    /* Request code used to invoke sign in user interactions. */
    private static final int GOOGLE_PLUS_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress
     * and prevents us from starting further intents.
     */
    private boolean mIntentInProgress;
    private Menu menu;
    private MenuItem searchItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.phoenix_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
       
    }

    @Override
    public void setContentView(int resourceId) {
        if (!isSlidingMenuEnabled()) {
            super.setContentView(resourceId);
            return;
        }

        View parentView = getLayoutInflater().inflate(R.layout.drawer_layout_framework, null);
        FrameLayout contentFrameLayout = (FrameLayout) parentView.findViewById(R.id.drawer_content_frame);
        getLayoutInflater().inflate(resourceId, contentFrameLayout, true);
        super.setContentView(parentView);
    }

    protected boolean isSlidingMenuEnabled() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            // Alter action
            case R.id.actionbar_alertAction:
                return true;
            // Search action
            case R.id.actionbar_searchAction:
                searchItem = menu.findItem(R.id.actionbar_searchAction);
                searchView = (SearchView) searchItem.getActionView();
                searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER);
                searchItem.expandActionView();
                setupSearchView(searchItem);
                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        //SearchView OnClose code comes here
                        return true;
                    }
                });
                return true;
            case android.R.id.home:
                this.finish();
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

    public boolean onQueryTextChange(String arg0) {
        // Code for text change comes here
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        // Code for text submission comes here
        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("Query", query);
        startActivity(intent);
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchItem.collapseActionView();
        return true;
    }
    public boolean onClose() {
        searchView.clearFocus();
        return false;
    }
    protected boolean isAlwaysExpanded() {
        return true;
    }

    //-------------------- Google Plus Sign In ------------------------//

    public void signInToGooglePlus() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        mGoogleApiClient.connect();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(),
                        GOOGLE_PLUS_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    public void onConnected(Bundle connectionHint) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
        getGooglePlusProfileInformation();
    }

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == GOOGLE_PLUS_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting() && responseCode < 0) {
                mGoogleApiClient.connect();
            }
        } else {
            super.onActivityResult(requestCode, responseCode, intent);
        }
    }

    private void getGooglePlusProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String data = currentPerson.getDisplayName();
                Preferences.setGooglePlusName(data);
                data = currentPerson.getImage().getUrl();
                Preferences.setGooglePlusPhotoUrl(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
