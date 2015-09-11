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

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.actions.BaseAction;
import com.phoenixmarketcity.android.phoenix.util.Utils;

/**
 * Created by Administrator on 7/2/2015.
 */
public class PopUpOfferCard extends PopupWindow

{
    private Popupdata mCardData;
    private final Context mContext;
    ImageView i;

    public PopUpOfferCard(Context context, Popupdata data) {
        super(context);
        mContext = context;
        mCardData = data;
        initView();

    }


    private void initView() {
        final LinearLayout mainLayout = new LinearLayout(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupview = inflater.inflate(R.layout.popupoffercard, null);

        final PopupWindow popupwindow = new PopupWindow(popupview, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        ImageView img1 = (ImageView) popupview.findViewById(R.id.shopoffer);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(((Activity) mContext).getWindow().getWindowManager().getDefaultDisplay().getWidth(), (((Activity) mContext).getWindow().getWindowManager().getDefaultDisplay().getHeight() / 3));
        img1.setLayoutParams(layoutParams);

        // get actionbar height
        int actionbarheight = ((ActionBarActivity) mContext).getSupportActionBar().getHeight();

        //get statusbar height
        Rect rectgle = new Rect();
        Window window = ((Activity) mContext).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        int StatusBarHeight = rectgle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int statusBar = contentViewTop - StatusBarHeight;

        //height and width of popup
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
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    }


    public static class Popupdata {
        private String mImageSrc;
        private BaseAction mActionObj;

        public Popupdata(String imgSrc, BaseAction actionObj) {
            mImageSrc = imgSrc;
            mActionObj = actionObj;
        }
    }
}
