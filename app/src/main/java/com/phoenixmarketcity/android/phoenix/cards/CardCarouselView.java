package com.phoenixmarketcity.android.phoenix.cards;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CardCarouselView extends BaseCardView {

    private TextView mDotsText[];
    public LinearLayout mDotsLayout,mTextLayout, actionItemLayout;

    public TextView textEventTitle,textEventDate,textEventTime,textEventSrtDesc;
    public TextView textEventName,textEventLive,textEventDate1,textEventTime1;

    public static void newInstance(final Context context, final String cardNumber,
                                    final CardViewCreatorCallback cb) {
        WebService.getInstance().getCardContents(cardNumber, new Callback<BaseCardView.CardViewData>() {
            @Override
            public void success(BaseCardView.CardViewData data, Response response) {
                if (BaseCardView.verifyCardData(data, 0)) {
                    CardCarouselView card = new CardCarouselView(context, data);
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

    public static CardCarouselView newInstance(final Context context,
                                   BaseCardView.CardViewData data) {
        CardCarouselView card = new CardCarouselView(context, data);
        return card;
    }

    // Constructor
    private CardCarouselView(Context context, BaseCardView.CardViewData data) {
        super(context, data);
    }

    @Override
    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardcarousel_view, this, true);

        mTextLayout=(LinearLayout)findViewById(R.id.mainTextLayout);
        actionItemLayout = (LinearLayout) findViewById(R.id.LinearLayout01);
        textEventTitle=(TextView)findViewById(R.id.eventTitle);
        textEventDate = (TextView) findViewById(R.id.evenDate);
        textEventTime = (TextView) findViewById(R.id.eventTime);
        textEventSrtDesc = (TextView) findViewById(R.id.eventShortDesc);

        /* setting event Cover Text */
         textEventName = (TextView) findViewById(R.id.eventName);
         textEventLive = (TextView) findViewById(R.id.eventLive);
         textEventDate1 = (TextView) findViewById(R.id.eventCrausalDate);
        textEventTime1 = (TextView) findViewById(R.id.eventCrausalTime);

        //For sliding images from Gallery
        Gallery gallery = (Gallery) findViewById(R.id.cardcarouselgallery);
        gallery.setAdapter(new ImageAdapter(getContext(), mCardObject.mCardData.mCardDataList));

        // Create the dots
        final int dotsCount = gallery.getAdapter().getCount();
        mDotsText = new TextView[dotsCount];

        // Set the dots
        mDotsLayout = (LinearLayout) findViewById(R.id.cardcarouselImage_count);
        for (int i = 0; i < dotsCount; i++) {
            mDotsText[i] = new TextView(getContext());
            mDotsText[i].setText("•");
            mDotsText[i].setTextSize(22);
            mDotsText[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            mDotsText[i].setTextColor(Color.GRAY);
            mDotsLayout.addView(mDotsText[i]);
        }

        // when we scroll the images we have to set the dot that corresponds
        // to the image to White and the others will be Gray
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int pos, long l) {
                for (int i = 0; i < dotsCount; i++) {
                    mDotsText[i].setTextColor(Color.GRAY);
                }
                mDotsText[pos].setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {
            }
        });

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardViewData.CardData.CardItemObject itemObject = mCardObject.mCardData.mCardDataList.get(position);
                handleClickAction(mContext, itemObject.mOfferingId, itemObject.mOfferingType);
            }
        });
    }

    // Image adapter
    public static class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private List<CardViewData.CardData.CardItemObject> mCardData;

        //constructor
        public ImageAdapter(Context context, List<CardViewData.CardData.CardItemObject> data) {
            mContext = context;
            mCardData = data;
        }

        public int getCount() {
            return mCardData.size();
        }

        public Object getItem(int i)
        {
            return i;
        }

        public long getItemId(int i) { return i; }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(mContext);
            Picasso.with(mContext).load(mCardData.get(i).mImageSrc)
                    .placeholder(R.drawable.placeholder_carousel).into(imageView);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new Gallery.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }
    }
}
