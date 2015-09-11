package com.phoenixmarketcity.android.phoenix.events;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.EventDetailsActivity;
import com.phoenixmarketcity.android.phoenix.booking.BookNowScreen;

/**
 * Created by Mansur on 7/5/2015.
 */

public class CardEventPerformView extends LinearLayout {

    // Creates new instance of this class
    public static CardEventPerformView newInstance(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        CardEventPerformView card = new CardEventPerformView(context, eventData);
        return card;
    }

    public CardEventPerformView(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        super(context);
        initView(context, eventData);
    }

    private void initView(final Context context, EventDetailsActivity.EventDescriptionData eventData) {

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card_event_perform, this, true);

        // Setting Text
        TextView mArtistName = (TextView) view.findViewById(R.id.card_EventArtistName);
        mArtistName.setText(eventData.mArtistDetails.get(0).mArtist_name);

        final TextView mGoldTicketPrice = (TextView) view.findViewById(R.id.card_EventGoldTicketPrice);
        mGoldTicketPrice.setText(eventData.mTicketDetails.get(0).mTicket_Price);

        final TextView mGoldTicketType = (TextView) findViewById(R.id.card_EventGoldTicketType);
        mGoldTicketType.setText(eventData.mTicketDetails.get(0).mTicket_Type);

        final TextView mSilverTicketPrice = (TextView) view.findViewById(R.id.card_EventSilverTicketPrice);
        mSilverTicketPrice.setText(eventData.mTicketDetails.get(1).mTicket_Price);

        final TextView mSilverTicketType = (TextView) view.findViewById(R.id.card_EventSilverTicketType);
        mSilverTicketType.setText(eventData.mTicketDetails.get(1).mTicket_Type);

        final TextView mWIPTTicketPrice = (TextView) view.findViewById(R.id.card_EventVVIPTicketPrice);
        mWIPTTicketPrice.setText(eventData.mTicketDetails.get(2).mTicket_Price);

        final TextView mWIPTTicketType = (TextView) view.findViewById(R.id.card_EventVVIPTicketType);
        mWIPTTicketType.setText(eventData.mTicketDetails.get(2).mTicket_Type);

        final String mEventTitle=eventData.mEventName.toString();
        final String mEventDate=eventData.mEventStartDate.toString();
        final String mEventTime=eventData.mEventDate.mEventTime.mStart;


        // Setting Button with Intent
        Button btn = (Button) findViewById(R.id.CardEventPerformButton);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String GoldTicketPrice = mGoldTicketPrice.getText().toString();
                String GoldTicketType = mGoldTicketType.getText().toString();
                String SilverTicketPrice = mSilverTicketPrice.getText().toString();
                String SilverTicketType = mSilverTicketType.getText().toString();
                String VIPTicketPrice = mWIPTTicketPrice.getText().toString();
                String VIPTicketType = mWIPTTicketType.getText().toString();
                Intent intent = new Intent(context, BookNowScreen.class);
                intent.putExtra("GPrice",GoldTicketPrice);
                intent.putExtra("GTicket",GoldTicketType);
                intent.putExtra("SPrice",SilverTicketPrice);
                intent.putExtra("STicket",SilverTicketType);
                intent.putExtra("VIPPrice",VIPTicketPrice);
                intent.putExtra("VIPTicket",VIPTicketType);
                intent.putExtra("EventTitle",mEventTitle);
                intent.putExtra("EventDate",mEventDate);
                intent.putExtra("EventTime",mEventTime);

                context.startActivity(intent);
            }
        });
    }
}
