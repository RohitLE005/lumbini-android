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


public class Card2x2View extends BaseCardView {

    public static void newInstance(final Context context, final String cardNumber,
                                   final CardViewCreatorCallback cb) {
        WebService.getInstance().getCardContents(cardNumber, new Callback<BaseCardView.CardViewData>() {
            @Override
            public void success(BaseCardView.CardViewData data, Response response) {
                if (BaseCardView.verifyCardData(data, 4)) {
                    Card2x2View card = new Card2x2View(context, data);
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
    private Card2x2View(Context context, BaseCardView.CardViewData data) {
        super(context, data);
    }

    // Initialize this card view
    @Override
    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_2x2, this, true);

        /* View card2x2 Title Id */
        TextView textView = (TextView) view.findViewById(R.id.card2x2TitleText);
        if (mCardObject.mCardData.mTitleObject == null ||
                TextUtils.isEmpty(mCardObject.mCardData.mTitleObject.mTitleText)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(mCardObject.mCardData.mTitleObject.mTitleText);
        }
        handleAction(mContext, view, mCardObject.mCardData.mTitleObject);

        CardViewData.CardData.CardItemObject cardItem = mCardObject.mCardData.mCardDataList.get(0);
        ImageView imageView = (ImageView) view.findViewById(R.id.card2x2ChildRow1Col1Image);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_2x2)
                .error(R.drawable.placeholder_2x2)
                .into(imageView);
        textView = (TextView) view.findViewById(R.id.card2x2ChildRow1Col1Title);
        textView.setText(cardItem.mTitleText);
        textView = (TextView) view.findViewById(R.id.card2x2ChildRow1Col1Subtitle);
        textView.setText(cardItem.mSubTitleText);
        View row1Col1ParentView = view.findViewById(R.id.card2x2ChildRow1Col1);
        handleAction(mContext, row1Col1ParentView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(1);
        imageView = (ImageView) view.findViewById(R.id.card2x2ChildRow1Col2Image);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_2x2)
                .error(R.drawable.placeholder_2x2)
                .into(imageView);
        textView = (TextView) view.findViewById(R.id.card2x2ChildRow1Col2Title);
        textView.setText(cardItem.mTitleText);
        textView = (TextView) view.findViewById(R.id.card2x2ChildRow1Col2Subtitle);
        textView.setText(cardItem.mSubTitleText);
        View row1Col2ParentView = view.findViewById(R.id.card2x2ChildRow1Col2);
        handleAction(mContext, row1Col2ParentView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(2);
        imageView = (ImageView) view.findViewById(R.id.card2x2ChildRow2Col1Image);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_2x2)
                .error(R.drawable.placeholder_2x2)
                .into(imageView);
        textView = (TextView) view.findViewById(R.id.card2x2ChildRow2Col1Title);
        textView.setText(cardItem.mTitleText);
        textView = (TextView) view.findViewById(R.id.card2x2ChildRow2Col1Subtitle);
        textView.setText(cardItem.mSubTitleText);
        View row2Col1ParentView = view.findViewById(R.id.card2x2ChildRow2Col1);
        handleAction(mContext, row2Col1ParentView, cardItem);

        cardItem = mCardObject.mCardData.mCardDataList.get(3);
        imageView = (ImageView) view.findViewById(R.id.card2x2ChildRow2Col2Image);
        Picasso.with(mContext).load(cardItem.mImageSrc)
                .placeholder(R.drawable.placeholder_2x2)
                .error(R.drawable.placeholder_2x2)
                .into(imageView);
        textView = (TextView) view.findViewById(R.id.card2x2ChildRow2Col2Title);
        textView.setText(cardItem.mTitleText);
        textView = (TextView) view.findViewById(R.id.card2x2ChildRow2Col2Subtitle);
        textView.setText(cardItem.mSubTitleText);
        View row2Col2ParentView = view.findViewById(R.id.card2x2ChildRow2Col2);
        handleAction(mContext, row2Col2ParentView, cardItem);
    }
}
