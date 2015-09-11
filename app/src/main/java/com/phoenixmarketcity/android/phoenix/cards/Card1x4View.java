package com.phoenixmarketcity.android.phoenix.cards;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mansur on 6/13/2015.
 */
public class Card1x4View extends BaseCardView {

    public static void newInstance(final Context context, final String cardNumber,
                                   final CardViewCreatorCallback cb) {
        WebService.getInstance().getCardContents(cardNumber, new Callback<BaseCardView.CardViewData>() {
            @Override
            public void success(BaseCardView.CardViewData data, Response response) {
                if (BaseCardView.verifyCardData(data, 4)) {
                    Card1x4View card = new Card1x4View(context, data);
                    cb.onSuccess(card);
                } else {
                    cb.onFailure();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d(cardNumber + " card creation failed with error: " +
                        error.getResponse() + "/" + error.getMessage() + "/" + error.getBody());
                cb.onFailure();
            }
        });
    }

    // Constructor
    private Card1x4View(Context context, BaseCardView.CardViewData data) {
        super(context, data);
    }

    // Initialize this card view
    @Override
    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.cardview_1x4, this, true);

        TextView titleTextView = (TextView) view.findViewById(R.id.card1x4TitleText);
        if (mCardObject.mCardData.mTitleObject == null ||
                TextUtils.isEmpty(mCardObject.mCardData.mTitleObject.mTitleText)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setText(mCardObject.mCardData.mTitleObject.mTitleText);
        }
        handleAction(mContext, view, mCardObject.mCardData.mTitleObject);

        CardViewData.CardData.CardItemObject cardItem = mCardObject.mCardData.mCardDataList.get(0);
        ImageView imageView = (ImageView) view.findViewById(R.id.card1x4ImageView1);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(1);
        imageView = (ImageView) view.findViewById(R.id.card1x4ImageView2);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(2);
        imageView = (ImageView) view.findViewById(R.id.card1x4ImageView3);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(3);
        imageView = (ImageView) view.findViewById(R.id.card1x4ImageView4);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);
    }
}
