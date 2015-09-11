package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.annotations.SerializedName;

import com.phoenixmarketcity.android.phoenix.booking.BookNowScreen;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.phoenixmarketcity.android.phoenix.R;

import org.parceler.Parcel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventCoverActivity extends BaseActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                onBackPressed();
                return true;
    }

    private ImageView mNextIconView;
    private LinearLayout mParentLayout;
    private List<EventCoverData> mEventData;
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_eventcover);

        WebService.getInstance().getCoverPageData("eventlist", new Callback<List<EventCoverData>>() {
            @Override
            public void success(List<EventCoverData> EventListObj, Response response) {
                mEventData = EventListObj;
                createEventCoverPage();
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d("Event list page loading failed with error: " +
                        error.getResponse() + "/" + error.getMessage() + "/" + error.getBody());

                String toastStr = String.format(EventCoverActivity.this.getResources().getString(
                        R.string.page_layout_fetch_error), " event list");
                Toast.makeText(EventCoverActivity.this, toastStr, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void createEventCoverPage() {
        final EventCoverData currentEventData = mEventData.get(mCurrentIndex);

        mParentLayout = (LinearLayout) findViewById(R.id.eventCoverParentLayoutId);
        Picasso.with(getBaseContext()).load(currentEventData.mImageSrc).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mParentLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                mParentLayout.setBackgroundResource(R.drawable.eventcover);
            }
        });

        TextView eventName = (TextView) findViewById(R.id.cardEventCoverArtistName);
        eventName.setText(currentEventData.mEventName);

        TextView eventText = (TextView) findViewById(R.id.cardEventCoverStatus);
        eventText.setText(currentEventData.mEventText);

        TextView eventDate = (TextView) findViewById(R.id.cardEventCoverDate);
        eventDate.setText(currentEventData.mEventDate);

        TextView eventTime = (TextView) findViewById(R.id.cardEventCoverTime);
        eventTime.setText(currentEventData.mEventTime);

        mNextIconView = (ImageView) findViewById(R.id.event_Next_Icon);
        mNextIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase counter to move to next Image
                incrementCurrentIndex();
                createEventCoverPage();
            }
        });

        Button viewEventCoverCard = (Button) findViewById(R.id.view_detail);
        viewEventCoverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventCoverActivity.this, EventDetailsActivity.class);
                startActivity(intent);
            }
        });

        Button durinEvent = (Button) findViewById(R.id.book_now);
        durinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(EventCoverActivity.this, BookNowScreen.class);
                intent.putExtra("eventname", currentEventData.mEventName);
                startActivity(intent);*/
            }
        });
    }

    private void incrementCurrentIndex() {
        mCurrentIndex = (mCurrentIndex + 1) % mEventData.size();
    }

    @Parcel
    public static class EventCoverData {

        @SerializedName("eventId")
        public String mEventId;

        @SerializedName("eventName")
        public String mEventName;

        @SerializedName("eventText")
        public String mEventText;

        @SerializedName("eventDate")
        public String mEventDate;

        @SerializedName("eventTime")
        public String mEventTime;

        @SerializedName("src")
        public String mImageSrc;
    }
}
