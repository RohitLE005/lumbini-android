package com.phoenixmarketcity.android.phoenix.events;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.ContestListActivity;
import com.phoenixmarketcity.android.phoenix.activities.EventContest_ThreeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2015-08-09.
 */
public class CardEventContestView extends LinearLayout {

    ImageView imgActive, imgChecked, imgExpired;
    private static final String CONTEST_ACTIVE = "active";

    public static CardEventContestView newInstance(Context context, ContestListActivity.EvenContestData eventData) {
        CardEventContestView card = new CardEventContestView(context, eventData);
        return card;
    }

    public CardEventContestView(Context context, ContestListActivity.EvenContestData eventData) {
        super(context);
        initView(context, eventData);
    }

    private void initView(final Context context, final ContestListActivity.EvenContestData eventData) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_event_contest, this, true);

        TextView contTxt = (TextView) view.findViewById(R.id.contestText);
        contTxt.setText(eventData.mContestactivity1);

        /* Event Contest changes */
        imgActive = (ImageView) view.findViewById(R.id.icon_active);
        imgChecked = (ImageView) view.findViewById(R.id.icon_check);
        imgExpired = (ImageView) view.findViewById(R.id.icon_expired);
        final String contestTime = eventData.mContestExpiredTime;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm");
            Date date = formatter.parse(contestTime);
            long millis = date.getTime();
            if (millis <= System.currentTimeMillis()) {
                imgActive.setVisibility(VISIBLE);
                imgChecked.setVisibility(GONE);
                imgExpired.setVisibility(GONE);
            } else if (millis >= System.currentTimeMillis() || millis == System.currentTimeMillis()) {
                imgExpired.setVisibility(VISIBLE);
                imgActive.setVisibility(GONE);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // setting of checked Icon
        imgActive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventData.mContest_Active.equalsIgnoreCase(CONTEST_ACTIVE)) {
                    imgChecked.setVisibility(VISIBLE);
                    imgExpired.setVisibility(GONE);
                    imgActive.setVisibility(GONE);
                    Intent lunchIntent = new Intent(context.getApplicationContext(), EventContest_ThreeActivity.class);
                    context.startActivity(lunchIntent);
                }
            }
        });
    }
}