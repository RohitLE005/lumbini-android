package com.phoenixmarketcity.android.phoenix.actions;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by neeraj.nayan on 12/06/15.
 */
public class ShopPageAction extends BaseAction {

    private String mPageUrl;

    public ShopPageAction(String pageUrl) {
        super(ACTION_TYPE_SHOP);
        mPageUrl = pageUrl;
    }

    public String getPageUrl() {
        return mPageUrl;
    }

    @Override
    public void execute(Context context) {
        Toast.makeText(context, "Executing action " + mPageUrl + ".", Toast.LENGTH_LONG).show();
    }

}
