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

public class Card2x4View extends BaseCardView {

    public static void newInstance(final Context context, final String cardNumber,
                                   final CardViewCreatorCallback cb) {
        WebService.getInstance().getCardContents(cardNumber, new Callback<CardViewData>() {
            @Override
            public void success(CardViewData data, Response response) {
                if (BaseCardView.verifyCardData(data, 8)) {
                    Card2x4View card = new Card2x4View(context, data);
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
    private Card2x4View(Context context, CardViewData data) {
        super(context, data);
    }

    // Initialize this card view
    @Override
    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.cardview_2x4, this, true);

        TextView titleTextView = (TextView) view.findViewById(R.id.card2x4TitleText);
        if (mCardObject.mCardData.mTitleObject == null ||
                TextUtils.isEmpty(mCardObject.mCardData.mTitleObject.mTitleText)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setText(mCardObject.mCardData.mTitleObject.mTitleText);
        }
        handleAction(mContext, view, mCardObject.mCardData.mTitleObject);

        CardViewData.CardData.CardItemObject cardItem = mCardObject.mCardData.mCardDataList.get(0);
        ImageView imageView = (ImageView) view.findViewById(R.id.card2x4ImageRow1View1);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(1);
        imageView = (ImageView) view.findViewById(R.id.card2x4ImageRow1View2);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(2);
        imageView = (ImageView) view.findViewById(R.id.card2x4ImageRow1View3);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(3);
        imageView = (ImageView) view.findViewById(R.id.card2x4ImageRow1View4);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(4);
        imageView = (ImageView) view.findViewById(R.id.card2x4ImageRow2View1);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(5);
        imageView = (ImageView) view.findViewById(R.id.card2x4ImageRow2View2);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(6);
        imageView = (ImageView) view.findViewById(R.id.card2x4ImageRow2View3);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(7);
        imageView = (ImageView) view.findViewById(R.id.card2x4ImageRow2View4);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x4)
                .error(R.drawable.placeholder_1x4)
                .into(imageView);
        handleAction(mContext, imageView, cardItem);
    }
}
