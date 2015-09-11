package com.phoenixmarketcity.android.phoenix.events;

import android.content.Context;
import android.widget.LinearLayout;

import com.phoenixmarketcity.android.phoenix.activities.EventDetailsActivity;

/**
 * Created by Lumbini on 06-08-2015.
 */
public class CardDuringEventDhamakaView extends LinearLayout {

    public static CardDuringEventDhamakaView newInstance(Context context,
                                                         EventDetailsActivity.EventDescriptionData eventData) {
        CardDuringEventDhamakaView card = new CardDuringEventDhamakaView(context, eventData);
        return card;
    }
    public CardDuringEventDhamakaView(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        super(context);
        initView(context, eventData);
    }

    private void initView(Context context, EventDetailsActivity.EventDescriptionData eventData) {
/*
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_eventduring_dhamaka, this, true);

        ImageView eventImage = (ImageView) view.findViewById(R.id.duringEventLiveAnimation);
        Picasso.with(context).load(eventData.mEventDetails.mLiveImage).into(eventImage);

        TextView eventLive = (TextView) view.findViewById(R.id.duringEventLive);
        eventLive.setText(eventData.mEventDetails.mStatus);

        TextView dhamaka = (TextView) view.findViewById(R.id.duringEventDhamakaText);
        dhamaka.setText(eventData.mEventDetails.mEventTitleText);

        TextView livenow = (TextView) view.findViewById(R.id.duringEventDhamakaLiveNow);
        livenow.setText(eventData.mEventDetails.mLiveNow);

        TextView shortDesc = (TextView) view.findViewById(R.id.duringEventDhamakaShortDesc);
        shortDesc.setText(eventData.mEventDetails.mShortDesc);

        TextView perform = (TextView) view.findViewById(R.id.duringEventPerform);
        perform.setText(eventData.mEventDetails.mPerformText);

        TextView artistName = (TextView) view.findViewById(R.id.duringEventArtistName);
        artistName.setText(eventData.mEventDetails.mArtistName);
*/
     }
}
