package com.phoenixmarketcity.android.phoenix.cards;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;
import com.squareup.picasso.Picasso;

import org.parceler.Parcel;

import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mujeeb on 21/06/15.
 */
public class CardStoreDetailsView extends CardView {

    private CardStoreDetailsViewData mCardData;
    private final Context mContext;
    private TextView mDotsText[];
    private LinearLayout mDotsLayout;

    public static void newInstance(final Context context, final String cardNumber,
                                   final CardViewCreatorCallback cb) {
        WebService.getInstance().getStoreDetailsCard(cardNumber, new Callback<CardStoreDetailsView.CardStoreDetailsViewData>() {
            @Override
            public void success(CardStoreDetailsViewData data, Response response) {

                if (verifyStoreDetailsViewData(data)) {
                    CardStoreDetailsView card = new CardStoreDetailsView(context, data);
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

    private CardStoreDetailsView(Context context, CardStoreDetailsViewData data) {
        super(context);
        mContext = context;
        mCardData = data;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.store_detail_view, this, true);

        //For sliding images from Gallery
        Gallery gallery = (Gallery) findViewById(R.id.shopDetailsCardGalleryView);
        gallery.setAdapter(new ImageAdapter(mContext, mCardData.mChildren));

        // Setting UI Parameters
        TextView textView = (TextView) findViewById(R.id.shopcardTitle);
        textView.setText(mCardData.mTitle);
        textView = (TextView) findViewById(R.id.shopcardSubTitle);
        textView.setText(mCardData.mSubTitle);
        textView = (TextView) findViewById(R.id.shopcardDesc);
        textView.setText(mCardData.mDescription);

        //For Button visibility
        Button buttonView = (Button) findViewById(R.id.shopcardButton);
        if(mCardData.mButtonText == null || mCardData.mButtonText == "") {
            buttonView.setVisibility(GONE);
        } else {
            buttonView.setVisibility(VISIBLE);
            buttonView.setText(mCardData.mButtonText);
        }

        // count the number of images we have to know how many dots we need
        final int dotsCount = gallery.getAdapter().getCount();
        mDotsText = new TextView[dotsCount];

        //set the dots
        mDotsLayout = (LinearLayout) findViewById(R.id.shopDetailsCardImageCount);
        for (int i = 0; i < dotsCount; i++) {
            mDotsText[i] = new TextView(getContext());
            mDotsText[i].setText("•");
            mDotsText[i].setTextSize(22);
            mDotsText[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            mDotsText[i].setTextColor(Color.GRAY);
            mDotsLayout.addView(mDotsText[i]);
        }

        //when we scroll the images we have to set the dot that
        // corresponds to the image to White and the others will be Gray
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
    }

    // Verify JSON data
    private static boolean verifyStoreDetailsViewData(CardStoreDetailsViewData data) {
        if (data.mChildren.size() == 0) return false;
        for (Iterator<CardStoreDetailsViewData.Card1x4ViewDataImageSrc> iterator = data.mChildren.iterator();
             iterator.hasNext();) {
            CardStoreDetailsViewData.Card1x4ViewDataImageSrc dataItem = iterator.next();
            if (dataItem.mImageSource == null) {
                iterator.remove();
            }
        }
        if (data.mChildren.size() == 0) return false;
        return true;
    }

    // Store Details View Data
    @Parcel
    public static class CardStoreDetailsViewData {
        @SerializedName("title")
        public String mTitle;
        @SerializedName("subtitle")
        public String mSubTitle;
        @SerializedName("desc")
        public String mDescription;
        @SerializedName("button")
        public String mButtonText = null;
        @SerializedName("children")
        public List<Card1x4ViewDataImageSrc> mChildren;

        @Parcel
        public static class Card1x4ViewDataImageSrc {
            @SerializedName("src")
            public String mImageSource;
        }
    }

    // Image adapter
    public static class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private List<CardStoreDetailsViewData.Card1x4ViewDataImageSrc> mCarouselData;

        //constructor
        public ImageAdapter(Context context, List<CardStoreDetailsViewData.Card1x4ViewDataImageSrc> data) {
            mContext = context;
            mCarouselData = data;
        }

        public int getCount() {
            return mCarouselData.size();
        }

        public Object getItem(int i)
        {
            return i;
        }

        public long getItemId(int i) { return i; }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(mContext);
            Picasso.with(mContext).load(mCarouselData.get(i).mImageSource).into(imageView);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new Gallery.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }
    }
}
