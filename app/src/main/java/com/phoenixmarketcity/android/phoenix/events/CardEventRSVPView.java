package com.phoenixmarketcity.android.phoenix.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phoenixmarketcity.android.phoenix.R;
/**
 * Created by Mansur on 7/5/2015.
 */
public class CardEventRSVPView extends LinearLayout {
    final TextView yesText ,noText,maybeText;

    public static CardEventRSVPView newInstance(Context context) {
        CardEventRSVPView card = new CardEventRSVPView(context);
        return card;
    }

    private CardEventRSVPView(final Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_rsvp, this, true);


        yesText = (TextView) findViewById(R.id.Card_RSVP_YesText);
        yesText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                yesText.setSelected(true);
                noText.setSelected(false);
                maybeText.setSelected(false);
                Toast.makeText(getContext(),"You have Clicked Yes for Event.", Toast.LENGTH_LONG).show();
            }
        });

        noText = (TextView) findViewById(R.id.Card_RSVP_NoText);
        noText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                yesText.setSelected(false);
                noText.setSelected(true);
                maybeText.setSelected(false);
                Toast.makeText(getContext(),"You have Clicked No for Event.", Toast.LENGTH_LONG).show();
            }
        });

        maybeText = (TextView) findViewById(R.id.Card_RSVP_MaybeText);
        maybeText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                yesText.setSelected(false);
                noText.setSelected(false);
                maybeText.setSelected(true);
                Toast.makeText(getContext(),"You have Clickeds MAYBE for Event.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
