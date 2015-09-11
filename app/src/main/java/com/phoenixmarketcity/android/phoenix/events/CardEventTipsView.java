package com.phoenixmarketcity.android.phoenix.events;

import android.content.Context;
import android.widget.LinearLayout;

import com.phoenixmarketcity.android.phoenix.activities.EventDetailsActivity;

/**
 * Created by Mansur on 7/5/2015.
 */
public class CardEventTipsView extends LinearLayout {

    int count = 0;

    public static CardEventTipsView newInstance(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        CardEventTipsView card = new CardEventTipsView(context, eventData);
        return card;
    }

    public CardEventTipsView(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        super(context);
        initView(context, eventData);
    }

    private void initView(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        /*
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview_tips, this, true);

        TextView tipsPoint1 = (TextView) view.findViewById(R.id.Card_TipsPoint1);
        tipsPoint1.setText(eventData.mEventDetails.mTip1);

        TextView tipsPoint2 = (TextView) view.findViewById(R.id.Card_TipsPoint2);
        tipsPoint2.setText(eventData.mEventDetails.mTip2);

        TextView tipsPoint3 = (TextView) view.findViewById(R.id.Card_TipsPoint3);
        tipsPoint3.setText(eventData.mEventDetails.mTip3);

        TextView tipsPoint4 = (TextView) view.findViewById(R.id.Card_TipsPoint4);
        tipsPoint4.setText(eventData.mEventDetails.mTip4);

        final TableLayout mTableList = (TableLayout) findViewById(R.id.tipsTextList);
        final ImageView mIconCollapse = (ImageView) findViewById(R.id.icon_collapse);
        final ImageView mIconExpand = (ImageView) findViewById(R.id.icon_expand);
        mIconCollapse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count == 1) {
                    mTableList.setVisibility(GONE);
                    mIconCollapse.setVisibility(GONE);
                    mIconExpand.setVisibility(VISIBLE);

                }
                mIconExpand.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count--;
                        if (count == 0)
                            mTableList.setVisibility(VISIBLE);
                        mIconCollapse.setVisibility(VISIBLE);
                        mIconExpand.setVisibility(GONE);
                    }
                });
            }
        });
*/
    }

}
