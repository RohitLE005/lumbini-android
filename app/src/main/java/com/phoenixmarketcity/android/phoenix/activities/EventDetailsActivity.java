package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.cards.BaseCardView;
import com.phoenixmarketcity.android.phoenix.cards.CardCarouselView;
import com.phoenixmarketcity.android.phoenix.events.CardEventPerformView;
import com.phoenixmarketcity.android.phoenix.events.CardEventRSVPView;
import com.phoenixmarketcity.android.phoenix.events.CardInviteFriendView;
import com.phoenixmarketcity.android.phoenix.events.CardPreEventDhamakaView;
import com.phoenixmarketcity.android.phoenix.events.Card_DescriptionView;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import org.parceler.Parcel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventDetailsActivity extends BaseActivity {

    // Static method to create intent to launch this activity
    public static Intent createLaunchIntent(final Context context, final int outletId) {
        final Intent intent = new Intent(context, EventDetailsActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, outletId);
        return intent;
    }

    public static final String EXTRA_EVENT_ID = GenericPageActivity.class.getName()
            + ".EXTRA.outletId";

    private View view;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    CardCarouselView carouselView;
    private int mEventId;
    private ViewGroup mParentLayout;
    private ProgressBar mProgressBar;
    public EventDescriptionData mEventData;
    public BaseCardView.CardViewData mCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_event_layout);

        mEventId = getIntent().getIntExtra(EXTRA_EVENT_ID, 0);
        if (mEventId ==0) {
            showErrorMessage();
            return;
        }
        mParentLayout = (LinearLayout) findViewById(R.id.storeCardListPageParentLayoutId);
        mProgressBar = (ProgressBar) findViewById(R.id.cardListPageProgressBarId);

        WebService.getInstance().getEventDetails(mEventId, new Callback<EventDescriptionData>() {
            @Override
            public void success(EventDescriptionData eventData, Response response) {
                if (eventData == null) {
                    showErrorMessage();
                    return;
                }

                mEventData = eventData;
                createEventCards();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d(mEventId + " page loading failed with error: " +
                        error.getResponse() + "/" + error.getMessage() + "/" + error.getBody());
                showErrorMessage();
            }
        });
        handleFooterNavigation();
    }

    private void showErrorMessage() {
        TextView errorTextView = (TextView) findViewById(R.id.error_text_view);
        errorTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void createEventCards() {
        setTitle(mEventData.mEventName);
        // Dispaly Card Event Data.
        CardPreEventDhamakaView cardEventData = CardPreEventDhamakaView.newInstance(this, mEventData);
        mParentLayout.addView(cardEventData);

        CardEventPerformView mPerformData = CardEventPerformView.newInstance(this,mEventData);
        mParentLayout.addView(mPerformData);
        // Display RSVP Card for EventDetailActivity
        CardEventRSVPView rsvpCard = CardEventRSVPView.newInstance(this);
        mParentLayout.addView(rsvpCard);

        // Display Invite Card for EventDetailActivity
        CardInviteFriendView friendData = CardInviteFriendView.newInstance(this);
        mParentLayout.addView(friendData);

        // Display Description Card for EventDetailActivity
        Card_DescriptionView descData = Card_DescriptionView.newInstance(this, mEventData);
        mParentLayout.addView(descData);

        /*
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(mEventData.mEventStartDate);
            long startDateInMillis = date.getTime();

            if (startDateInMillis <= System.currentTimeMillis()) {

            } else if (startDateInMillis == System.currentTimeMillis()) {
                CardDuringEventDhamakaView duringEventData = CardDuringEventDhamakaView.newInstance(this, mEventData);
                mParentLayout.addView(duringEventData);

                CardDuringEventMediaLiveActionView mediaData = CardDuringEventMediaLiveActionView.newInstance(this);
                mParentLayout.addView(mediaData);
            } else {
                CardPostAnimationView animationData = CardPostAnimationView.newInstance(this, mEventData);
                mParentLayout.addView(animationData);

                CardDuringEventMediaLiveActionView mediaData1 = CardDuringEventMediaLiveActionView.newInstance(this);
                mParentLayout.addView(mediaData1);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }*/
    }

    // Handles footer bar navigation
    private void handleFooterNavigation() {
        // On Home Activity

        Button footerButton = (Button) findViewById(R.id.FooterButtonDiscuss);
        footerButton.setSelected(true);

        footerButton = (Button) findViewById(R.id.FooterButtonHome);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(EventDetailsActivity.this, LandingPageActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });
        footerButton = (Button) findViewById(R.id.FooterButtonDiscuss);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(EventDetailsActivity.this, CommentsActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        footerButton = (Button) findViewById(R.id.FooterButtonContest);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent launchIntent = new Intent(EventDetailsActivity.this, EventContestActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);*/
            }
        });

        footerButton = (Button) findViewById(R.id.FooterButtonDo);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(EventDetailsActivity.this, DoActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });


    }

    @Parcel
    public static class EventDescriptionData {
        @SerializedName("event_id")
        public String mEventId;
        @SerializedName("event_name")
        public String mEventName;
        @SerializedName("event_description")
        public String mEventDescription;
        @SerializedName("event_start_date")
        public String mEventStartDate;
        @SerializedName("event_end_date")
        public String mEventEndDate;
        @SerializedName("event_picture")
        public String mEventImage;
        @SerializedName("artist_name")
        public List<String> mArtistName;

        @SerializedName("artist_detail")
        public List<ArtistDetails> mArtistDetails;
        // Event Artist Details
        @Parcel
        public static class ArtistDetails {
            @SerializedName("artist_name")
            public String mArtist_name;
            @SerializedName("artist_bio")
            public String mArtist_bio;
        }
        // Event Date And Time
        @SerializedName("event_timings")
        public EventDate mEventDate;

        @Parcel
        public static class EventDate {
            @SerializedName("2015-08-22")
            public EventTime mEventTime;

            @Parcel
            public static class EventTime {
                @SerializedName("start")
                public String mStart;
                @SerializedName("end")
                public String mEnd;
            }
        }

        // Event Ticket Details
        @SerializedName("ticketDatail")
        public List<TicketDetails> mTicketDetails;
        @Parcel
        public static class TicketDetails {
            @SerializedName("ticket_type")
            public String mTicket_Type;
            @SerializedName("ticket_price")
            public String mTicket_Price;
            @SerializedName("number_of_ticket")
            public String mNumber_Of_Ticket;
        }

    }
}