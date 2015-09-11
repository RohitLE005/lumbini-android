package com.phoenixmarketcity.android.phoenix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.cards.CardEventTicketView;
import com.phoenixmarketcity.android.phoenix.util.Utils;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;


public class PopUpEventTicket extends PopupWindow

{
   
    private static final String TICKET_TYPE = "gold";
    private WeakReference<CardEventTicketView.CardEventTicketViewData> mCardData;
    private final Context mContext;
    ActionBarActivity actionBarActivity;
    ImageView i;

    public PopUpEventTicket(Context context,CardEventTicketView.CardEventTicketViewData data) {
        super(context);
        mContext = context;
        mCardData = new WeakReference<CardEventTicketView.CardEventTicketViewData>(data);
        initView();

    }


    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupview = inflater.inflate(R.layout.popupeventticket, null);

        ImageView eventImage = (ImageView)popupview.findViewById(R.id.cardViewEventPopTicketImage);
        Picasso.with(mContext).load(mCardData.get().mEventPhoto).into(eventImage);

        ImageView barcodeImage = (ImageView)popupview.findViewById(R.id.cardviewEventPopTicketBarcode);
        Picasso.with(mContext).load(mCardData.get().mBarcode).into(barcodeImage);

        TextView eventName = (TextView) popupview.findViewById(R.id.cardviewEventPopTicketName);
        eventName.setText(mCardData.get().mEventName);

        TextView eventTime = (TextView)popupview.findViewById(R.id.cardviewEventPopTicketTime);
        eventTime.setText(mCardData.get().mEventTime);

        TextView ticketCount = (TextView) popupview.findViewById(R.id.cardviewEventPopTicketQuantity);
        ticketCount.setText(mCardData.get().mTicketCount);

        TextView eventDate = (TextView)popupview.findViewById(R.id.cardviewEventPopTicketDate);
        eventDate.setText(mCardData.get().mEventDate);

        ImageView ticketType = (ImageView)popupview.findViewById(R.id.cardviewEventPopTickettype);
        Picasso.with(mContext).load(mCardData.get().mTicketType).into(ticketType);

        if(mCardData.get().mTicketType.equals(TICKET_TYPE)) {
            ticketType.setImageResource(R.drawable.icon_event_gold);
        }

        ImageView img1 = (ImageView) popupview.findViewById(R.id.cardViewEventPopTicketImage);
        final LinearLayout mainLayout = new LinearLayout(mContext);
        final PopupWindow popupwindow = new PopupWindow(popupview, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(((Activity) mContext).getWindow().getWindowManager().getDefaultDisplay().getWidth(), (((Activity) mContext).getWindow().getWindowManager().getDefaultDisplay().getHeight() / 3));
        img1.setLayoutParams(layoutParams);

        //get status bar height
        Rect rectgle = new Rect();
        Window window = ((Activity) mContext).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        int StatusBarHeight = rectgle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int statusBar = contentViewTop - StatusBarHeight;

        //navbar height
        //get actionbar height
        int actionbarheight = ((ActionBarActivity) mContext).getSupportActionBar().getHeight();

        //height and width of popup window
        popupwindow.setWidth(Utils.getPopupDialogWidth((Activity) mContext));
        popupwindow.setHeight(Utils.getWindowHeight((Activity) mContext) - actionbarheight - statusBar);
        popupwindow.showAtLocation(mainLayout, Gravity.BOTTOM, 0, 0);

        //To close PopUpWindow
        ImageView imgClose = (ImageView) popupview.findViewById(R.id.close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupwindow.dismiss();
            }
        });
        LinearLayout shareBtn = (LinearLayout) popupview.findViewById(R.id.ShareButton);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "I have got a new and exiting offer";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hi there");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    }



}
