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
 * Created by neeraj.nayan on 12/06/15.
 */
public class Card1x1View extends BaseCardView {

    public static void newInstance(final Context context, final String cardNumber,
                                   final CardViewCreatorCallback cb) {
//        WebService.getInstance().get1x1Card(cardNumber, new Callback<CardViewData>() {
        WebService.getInstance().getCardContents(cardNumber, new Callback<BaseCardView.CardViewData>() {
            @Override
            public void success(BaseCardView.CardViewData data, Response response) {
                if (BaseCardView.verifyCardData(data, 1)) {
                    Card1x1View card = new Card1x1View(context, data);
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
    private Card1x1View(Context context, BaseCardView.CardViewData data) {
        super(context, data);
    }

    // Initialize this card view
    @Override
    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.cardview_1x1, this, true);

        TextView titleTextView = (TextView) view.findViewById(R.id.card1x1TitleText);
        if (mCardObject.mCardData.mTitleObject == null ||
                TextUtils.isEmpty(mCardObject.mCardData.mTitleObject.mTitleText)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setText(mCardObject.mCardData.mTitleObject.mTitleText);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.card1x1ImageView);
        Picasso.with(mContext).load(mCardObject.mCardData.mCardDataList.get(0).mImageSrc)
                .placeholder(R.drawable.placeholder_1x1)
                .error(R.drawable.placeholder_1x1)
                .into(imageView);

        handleAction(mContext, view, mCardObject.mCardData.mCardDataList.get(0));
    }
}
