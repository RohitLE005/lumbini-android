package com.phoenixmarketcity.android.phoenix.cards;

import android.view.ViewGroup;

/**
 * Created by neeraj.nayan on 12/06/15.
 */
public interface CardViewCreatorCallback {
    public void onSuccess(ViewGroup cardView);
    public void onFailure();
}
