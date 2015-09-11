package com.phoenixmarketcity.android.phoenix.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.PopUpOfferCard;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import org.parceler.Parcel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mansur on 6/27/2015.
 */
public class CardParkingView extends LinearLayout {

    private PopUpOfferCard.Popupdata p;
    private CardParkingViewData mCardData;
    private final Context mContext;
    private static final String VEHICLE_TYPE_SEDAN = "sedan";

    public static void newInstance(final Context context, final String cardNumber,
                                   final CardViewCreatorCallback cb) {
        WebService.getInstance().getParkingTicketCard(cardNumber, new Callback<CardParkingViewData>() {
            @Override
            public void success(CardParkingViewData data, Response response) {

                if (verifyCardParkingViewData(data)) {
                    CardParkingView card = new CardParkingView(context,data);
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

    public CardParkingView(Context context, CardParkingViewData data) {
        super(context);
        mContext = context;
        mCardData = data;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_parking, this, true);


        TextView parkingDuration = (TextView) findViewById(R.id.cardviewParkingQuintity);
        parkingDuration.setText(mCardData.mduration);


        TextView parkingDate = (TextView) findViewById(R.id.cardviewParkingDate);
        parkingDate.setText(mCardData.mparkingdate);

        TextView parkingTime = (TextView) findViewById(R.id.cardviewParkingTime);
        parkingTime.setText(mCardData.mparkingtime);

        TextView vehicleNumber = (TextView) findViewById(R.id.cardviewParkingReservation);
        vehicleNumber.setText(mCardData.mvehiclenumber);


        ImageView vehicleType = (ImageView) findViewById(R.id.cardviewParkingCarSymbol);


        if(mCardData.mvehicletype.equalsIgnoreCase(VEHICLE_TYPE_SEDAN)) {
            vehicleType.setImageResource(R.drawable.icon_parking_cartype);
        }


        ImageView cardviewParkingSymbol = (ImageView) findViewById(R.id.cardviewParkingSymbol);
        cardviewParkingSymbol.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpOfferCard pop = new PopUpOfferCard(mContext,p);
            }
        });
    }

    public static boolean verifyCardParkingViewData(CardParkingViewData data) {
        if (data.mvehiclenumber == null || data.mparkingtime == null || data.mvehicletype == null || data.mparkingdate == null || data.mduration == null || data.mvehicletype == null)
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


    // Parking ticket View Data
    @Parcel
    public static class CardParkingViewData {
        @SerializedName("vehiclenumber")
        public String mvehiclenumber;
        @SerializedName("parkingtime")
        public String mparkingtime;
        @SerializedName("vehicletype")
        public String mvehicletype;
        @SerializedName("parkingdate")
        public String mparkingdate;
        @SerializedName("duration")
        public String mduration;

    }

}
