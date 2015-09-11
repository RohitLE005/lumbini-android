package com.phoenixmarketcity.android.phoenix.actions;

import android.content.Context;

/**
 * Created by neeraj.nayan on 12/06/15.
 */
public class BaseAction {
    public final static int ACTION_TYPE_SHOP = 0x01;

    private int mActionType;

    public BaseAction(int actionType) {
        mActionType = actionType;
    }

    public int getActionType() {
        return mActionType;
    }

    public void execute(Context context) {
        // Do nothing
        // Base class to override
    }
}
