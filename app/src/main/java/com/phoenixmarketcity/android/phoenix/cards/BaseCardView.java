package com.phoenixmarketcity.android.phoenix.cards;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.activities.BrandsActivity;
import com.phoenixmarketcity.android.phoenix.activities.EventDetailsActivity;
import com.phoenixmarketcity.android.phoenix.activities.GenericPageActivity;
import com.phoenixmarketcity.android.phoenix.activities.MovieDetailsActivity;
import com.phoenixmarketcity.android.phoenix.util.Utils;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by neeraj.nayan on 12/06/15.
 */
public abstract class BaseCardView extends LinearLayout {

    protected CardViewData mCardObject;
    protected final Context mContext;

    // Constructor
    protected BaseCardView(Context context, CardViewData data) {
        super(context);
        mContext = context;
        mCardObject = data;
        initView();
    }

    // Initializes the views
    protected abstract void initView();

    // Verify card data
    public static boolean verifyCardData(CardViewData data, int childCount) {
        if (data.mCardData == null) return false;
        if (data.mCardData.mCardDataList == null || (childCount > 0 && data.mCardData.mCardDataList.size() != childCount)) return false;
        for (CardViewData.CardData.CardItemObject cardItem : data.mCardData.mCardDataList) {
            if (TextUtils.isEmpty(cardItem.mImageSrc)) return false;
        }
        return true;
    }

    // Handles action on card children
    protected void handleAction(final Context context, View view, final CardViewData.CardData.CardItemObject cardData) {
        if (cardData.mActionObj == null || cardData.mActionObj.size() == 0) return;
        for (int i=0; i<cardData.mActionObj.size(); i++) {
            if (cardData.mActionObj.get(0).equalsIgnoreCase("click")) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleClickAction(context, cardData.mOfferingId, cardData.mOfferingType);
                    }
                });
            }
        }
    }

    // Handle click action
    protected void handleClickAction(final Context context, final String offeringId, final String offeringType) {
        if (offeringType.equalsIgnoreCase("store")) {
            if (offeringId.equalsIgnoreCase("all")) {
                Intent launchIntent = new Intent(context, BrandsActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(launchIntent);
            } else {
                context.startActivity(GenericPageActivity.createLaunchIntent(context, offeringId));
            }
            return;
        }

        int id = Utils.getInteger(offeringId);
        if (id == 0) return;
        if (offeringType.equalsIgnoreCase("event")) {
            context.startActivity(EventDetailsActivity.createLaunchIntent(context, id));
        } else if (offeringType.equalsIgnoreCase("movie")) {
            context.startActivity(MovieDetailsActivity.createLaunchIntent(context, id));
        }
    }

    @Parcel
    public static class CardViewData {
        @SerializedName("card_data")
        public CardData mCardData;

        @Parcel
        public class CardData {
            @SerializedName("main_title")
            public CardItemObject mTitleObject;
            @SerializedName("info")
            public List<CardItemObject> mCardDataList;

            @Parcel
            public class CardItemObject {
                @SerializedName("title")
                public String mTitleText;
                @SerializedName("sub_title")
                public String mSubTitleText;
                @SerializedName("image")
                public String mImageSrc;
                @SerializedName("offeringtype")
                public String mOfferingType;
                @SerializedName("offeringId")
                public String mOfferingId;
                @SerializedName("action")
                public List<String> mActionObj;
            }
        }
    }
}
