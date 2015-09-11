package com.phoenixmarketcity.android.phoenix.events;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.EventDetailsActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by Lumbini on 29-07-2015.
 */
public class CardPreEventDhamakaView extends LinearLayout {


    public static CardPreEventDhamakaView newInstance(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        CardPreEventDhamakaView card = new CardPreEventDhamakaView(context, eventData);
        return card;
    }

    private CardPreEventDhamakaView(final Context context, EventDetailsActivity.EventDescriptionData eventData) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_preevent_dhamaka, this, true);

        ImageView mEventImage = (ImageView) findViewById(R.id.eventImage);
        Picasso.with(context).load(eventData.mEventImage)
                .placeholder(R.drawable.placeholder_1x1)
                .error(R.drawable.placeholder_1x1)
                .into(mEventImage);

        TextView eventText = (TextView) findViewById(R.id.eventTitle);
        eventText.setText(eventData.mEventName);

        TextView eventDate = (TextView) findViewById(R.id.evenDate);
        eventDate.setText(eventData.mEventStartDate);

        TextView eventTime = (TextView) findViewById(R.id.eventTime);
        eventTime.setText(eventData.mEventDate.mEventTime.mStart);

        TextView eventShortDesc = (TextView) findViewById(R.id.eventShortDesc);
        eventShortDesc.setText(eventData.mArtistDetails.get(0).mArtist_bio);
    }
}
