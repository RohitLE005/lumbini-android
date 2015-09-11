package com.phoenixmarketcity.android.phoenix.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.EventDetailsActivity;

/**
 * Created by Mansur on 7/4/2015.
 */
public class Card_DescriptionView extends LinearLayout {

    int count = 0;
    // Creates new instance of this class
    public static Card_DescriptionView newInstance(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        Card_DescriptionView card = new Card_DescriptionView(context, eventData);
        return card;
    }

    public Card_DescriptionView(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        super(context);
        initView(context, eventData);
    }

    private void initView(final Context context, EventDetailsActivity.EventDescriptionData eventData) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_description, this, true);

        final TextView descriptionText = (TextView) findViewById(R.id.cardviewDescriptionText);
        descriptionText.setText(eventData.mEventDescription);

        final ImageView icon_collapse = (ImageView) findViewById(R.id.icon_collapse);
        final ImageView icon_expand = (ImageView) findViewById(R.id.icon_expand);
        icon_collapse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count == 1) {
                    descriptionText.setVisibility(GONE);
                    icon_collapse.setVisibility(GONE);
                    icon_expand.setVisibility(VISIBLE);

                }
                icon_expand.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count--;
                        if (count == 0)
                            descriptionText.setVisibility(VISIBLE);
                        icon_collapse.setVisibility(VISIBLE);
                        icon_expand.setVisibility(GONE);
                    }
                });
            }

        });
    }
}
