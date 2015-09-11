package com.phoenixmarketcity.android.phoenix.cards;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by neeraj.nayan on 11/07/15.
 */
public class CardUtils {
    // Create card
    public static void createCard(Context context, CardConfiguration.LayoutConfiguration card, CardViewCreatorCallback cb) {
        switch(card.mCardType) {
            case "card_1x1_view":
                Card1x1View.newInstance(context, card.mCardId, cb);
                break;
            case "card_1x2_view":
                Card1x2View.newInstance(context, card.mCardId, cb);
                break;
            case "card_1x3_view":
                Card1x3View.newInstance(context, card.mCardId, cb);
                break;
            case "card_1x4_view":
                Card1x4View.newInstance(context, card.mCardId, cb);
                break;
            case "card_2x2_view":
                Card2x2View.newInstance(context, card.mCardId, cb);
                break;
            case "card_carousel":
                CardCarouselView.newInstance(context, card.mCardId, cb);
                break;
            case "card_Eventticket_view":
                CardEventTicketView.newInstance(context, card.mCardId, cb);
                break;
            case "card_ParkingReservation_view":
                CardParkingView.newInstance(context, card.mCardId, cb);
                break;
            case "card_store_details_view":
                CardStoreDetailsView.newInstance(context, card.mCardId, cb);
                break;
            default:
                cb.onFailure();
        }
    }

    public static boolean isCardListPageValid(CardUtils.CardConfiguration cardConfiguration) {
        if (cardConfiguration == null || cardConfiguration.mLayoutConfiguration == null
                || cardConfiguration.mLayoutConfiguration.size() == 0) return false;
        return true;
    }

    // Page layout configuration
    // Should replicate the respective JSON structure
    public static class CardConfiguration {
        @SerializedName("page_title")
        public String mPageTitle;
        @SerializedName("page_type")
        public String mPageType;
        @SerializedName("layout")
        public List<LayoutConfiguration> mLayoutConfiguration;

        public static class LayoutConfiguration {
            @SerializedName("card_type")
            public String mCardType;
            @SerializedName("card_id")
            public String mCardId;
        }
    }
}
