package com.phoenixmarketcity.android.phoenix.util;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

import com.phoenixmarketcity.android.phoenix.R;

/**
 * Created by neeraj.nayan on 19/08/15.
 */
public class Utils {
    private static int mWindowWt = 0;
    private static int mWindowHt = 0;

    public static int getWindowWidth(Activity activity) {
        if (mWindowWt == 0) {
            updateWindowDimension(activity);
        }
        return mWindowWt;
    }

    public static int getWindowHeight(Activity activity) {
        if (mWindowHt == 0) {
            updateWindowDimension(activity);
        }
        return mWindowHt;
    }

    public static int getPopupDialogWidth(Activity mActivity) {
        int padding = mActivity.getResources().getDimensionPixelSize(R.dimen.popup_dialog_side_padding);
        return getWindowWidth(mActivity) - padding;
    }

    public static int getInteger(String data) {
        try {
            int number = Integer.parseInt(data, 10);
            return number;
        } catch(Exception e) {
            return 0;
        }
    }

    //----------------- PRIVATE Functions ------------------
    private static void updateWindowDimension(Activity mActivity) {
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWindowWt = size.x;
        mWindowHt = size.y;
    }
}
