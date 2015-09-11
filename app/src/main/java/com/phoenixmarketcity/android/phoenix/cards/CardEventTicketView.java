package com.phoenixmarketcity.android.phoenix.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.PopUpEventTicket;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;
import com.squareup.picasso.Picasso;

import org.parceler.Parcel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mansur on 12/06/15.
 */
public class CardEventTicketView extends LinearLayout {

    private CardEventTicketViewData mCardData;
    private final Context mContext;
    private static final String TICKET_TYPE = "gold";


    public static void newInstance(final Context context, final String cardNumber,
                                   final CardViewCreatorCallback cb) {
        WebService.getInstance().getEventTicketCard(cardNumber, new Callback<CardEventTicketViewData>() {
            @Override
            public void success(CardEventTicketViewData data, Response response) {

                if (verifyCardEventViewData(data)) {
                    CardEventTicketView card = new CardEventTicketView(context, data);
                    cb.onSuccess(card);
                } else {
                    Logger.d("Card " + cardNumber + " creation failed.");
                    cb.onFailure();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d("Card " + cardNumber + " creation failed.");
                cb.onFailure();
            }
        });
    }


    public CardEventTicketView(Context context, CardEventTicketViewData data) {
        super(context);
        mContext = context;
        mCardData = data;
        initView();
    }
    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_eventticket, this, true);

        TextView ticketCount = (TextView) findViewById(R.id.cardviewEventTicketQuantity);
        ticketCount.setText(mCardData.mTicketCount);

        TextView eventName = (TextView) findViewById(R.id.cardviewEventTicketName);
        eventName.setText(mCardData.mEventName);

        TextView eventTime = (TextView) findViewById(R.id.cardviewEventTicketTime);
        eventTime.setText(mCardData.mEventTime);

        TextView eventDate = (TextView) findViewById(R.id.cardviewEventTicketDate);
        eventDate.setText(mCardData.mEventDate);

        ImageView ticketType = (ImageView)findViewById(R.id.cardviewEventTickettype);
        Picasso.with(mContext).load(mCardData.mTicketType).into(ticketType);

        if(mCardData.mTicketType.equals(TICKET_TYPE)) {
            ticketType.setImageResource(R.drawable.icon_event_gold);
        }

        LinearLayout mLinearView = (LinearLayout) findViewById(R.id.cardEventTicketParentView);
        mLinearView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpEventTicket pop = new PopUpEventTicket(mContext, mCardData);

            }
        });


    }

    public static boolean verifyCardEventViewData(CardEventTicketViewData data) {
        if(data.mEventDate==null||data.mEventName==null||data.mEventTime==null||data.mTicketCount==null||data.mTicketType==null||data.mTicketType==null||data.mEventPhoto==null||data.mBarcode==null)
           return false;
        else
           return true;
    }




   /* // Verify JSON data
    private static boolean verifyCardEventViewData(CardEventTicketViewData data) {
        if (data.mChildren.size() == 0) return false;
        for (Iterator<CardEventTicketViewData.CardEventViewDataImageSrc> iterator = data.mChildren.iterator();
             iterator.hasNext();) {
            CardEventTicketViewData.CardEventViewDataImageSrc dataItem = iterator.next();
            if (dataItem.mImageSource == null) {
                iterator.remove();
            }
        }
        if (data.mChildren.size() == 0) return false;
        return true;
    }*/



    // Event ticket View Data
    @Parcel
    public static class CardEventTicketViewData {
        @SerializedName("eventname")
        public String mEventName;
        @SerializedName("eventtime")
        public String mEventTime;
        @SerializedName("eventdate")
        public String mEventDate;
        @SerializedName("ticketcount")
        public String mTicketCount;
        @SerializedName("tickettype")
        public String mTicketType;
        @SerializedName("eventphoto")
        public String mEventPhoto;
        @SerializedName("barcode")
        public String mBarcode;


    }
}


