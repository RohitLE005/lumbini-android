package com.phoenixmarketcity.android.phoenix.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.events.CardEventContestView;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import org.parceler.Parcel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ContestListActivity extends BaseActivity {

    private ViewGroup mParentLayout;
    private ProgressBar mProgressBar;
    private String urlObjName = "29_EventContest_Collapsed";
    private EvenContestData mEventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_generic_layout);

        mParentLayout = (LinearLayout) findViewById(R.id.storeCardListPageParentLayoutId);
        mProgressBar = (ProgressBar) findViewById(R.id.cardListPageProgressBarId);

        WebService.getInstance().getContestList(urlObjName, new Callback<EvenContestData>() {
            @Override
            public void success(EvenContestData eventObj, Response response) {
                mEventData = eventObj;
                createCardEventContest();
                updateProgressBar();

            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d(urlObjName + " page loading failed with error: " +
                        error.getResponse() + "/" + error.getMessage() + "/" + error.getBody());

                String toastStr = String.format(ContestListActivity.this.getResources().getString(
                        R.string.page_layout_fetch_error), urlObjName);
                Toast.makeText(ContestListActivity.this, toastStr, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    /* Adding Cards in Layout */
    private void createCardEventContest() {
        getSupportActionBar().setTitle(mEventData.mContest_Title);

        CardEventContestView contestdata = CardEventContestView.newInstance(this,mEventData );
        mParentLayout.addView(contestdata);

        CardEventContestView contestdata1 = CardEventContestView.newInstance(this,mEventData );
        mParentLayout.addView(contestdata1);

        CardEventContestView contestdata2 = CardEventContestView.newInstance(this,mEventData );
        mParentLayout.addView(contestdata2);

        CardEventContestView contestdata3 = CardEventContestView.newInstance(this,mEventData );
        mParentLayout.addView(contestdata3);

        CardEventContestView contestdata4 = CardEventContestView.newInstance(this,mEventData );
        mParentLayout.addView(contestdata4);

        CardEventContestView contestdata5 = CardEventContestView.newInstance(this,mEventData );
        mParentLayout.addView(contestdata5);

    }

    private void updateProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


    @Parcel
    public static class EvenContestData {
    @SerializedName("contestactivityactive1")
    public String mContestactivity1;


       @SerializedName("contestactivityactive2")
       public String mContestactiviy2;

       @SerializedName("contestactivitycompleted1")
       public String mContestactivity3;

       @SerializedName("contestactivitycompleted2")
       public String mContestactivity4;

       @SerializedName("contestactivityexpired")
       public String mContestactivity5;

        @SerializedName("contest_state_Active")
        public String mContest_Active;

        @SerializedName("contest_expired_Time")
        public String mContestExpiredTime;

        @SerializedName("contest_Title")
        public String mContest_Title;



    }
}