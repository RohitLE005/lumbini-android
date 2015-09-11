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

public class Card1x3View extends BaseCardView {

    public static void newInstance(final Context context, final String cardNumber,
                                   final CardViewCreatorCallback cb) {
        WebService.getInstance().getCardContents(cardNumber, new Callback<BaseCardView.CardViewData>() {
            @Override
            public void success(BaseCardView.CardViewData data, Response response) {
                if (BaseCardView.verifyCardData(data, 3)) {
                    Card1x3View card = new Card1x3View(context, data);
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
    private Card1x3View(Context context, BaseCardView.CardViewData data) {
        super(context, data);
    }

    // Initialize this card view
    @Override
    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_1x3, this, true);

        TextView titleTextView = (TextView) view.findViewById(R.id.card1x3TitleText);
        if (mCardObject.mCardData.mTitleObject == null ||
                TextUtils.isEmpty(mCardObject.mCardData.mTitleObject.mTitleText)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setText(mCardObject.mCardData.mTitleObject.mTitleText);
        }
        handleAction(mContext, view, mCardObject.mCardData.mTitleObject);

        CardViewData.CardData.CardItemObject cardItem = mCardObject.mCardData.mCardDataList.get(0);
        ImageView imageView = (ImageView) view.findViewById(R.id.card1x3Col1ImageView);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x3)
                .error(R.drawable.placeholder_1x3)
                .into(imageView);
        TextView img1TitleText1 = (TextView) view.findViewById(R.id.card1x3Col1TitleText);
        img1TitleText1.setText(cardItem.mTitleText);
        TextView img1SubtitleText = (TextView) view.findViewById(R.id.card1x3Col1SubtitleText);
        img1SubtitleText.setText(cardItem.mSubTitleText);
        View col1ViewGroup = view.findViewById(R.id.card1x3Col1);
        handleAction(mContext, col1ViewGroup, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(1);
        imageView = (ImageView) view.findViewById(R.id.card1x3Col2ImageView);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x3)
                .error(R.drawable.placeholder_1x3)
                .into(imageView);
        img1TitleText1 = (TextView) view.findViewById(R.id.card1x3Col2TitleText);
        img1TitleText1.setText(cardItem.mTitleText);
        img1SubtitleText = (TextView) view.findViewById(R.id.card1x3Col2SubtitleText);
        img1SubtitleText.setText(cardItem.mSubTitleText);
        View col2ViewGroup = view.findViewById(R.id.card1x3Col2);
        handleAction(mContext, col2ViewGroup, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(2);
        imageView = (ImageView) view.findViewById(R.id.card1x3Col3ImageView);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_1x3)
                .error(R.drawable.placeholder_1x3)
                .into(imageView);
        img1TitleText1 = (TextView) view.findViewById(R.id.card1x3Col3TitleText);
        img1TitleText1.setText(cardItem.mTitleText);
        img1SubtitleText = (TextView) view.findViewById(R.id.card1x3Col3SubtitleText);
        img1SubtitleText.setText(cardItem.mSubTitleText);
        View col3ViewGroup = view.findViewById(R.id.card1x3Col3);
        handleAction(mContext, col3ViewGroup, cardItem);
    }
}
