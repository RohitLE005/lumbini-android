package com.phoenixmarketcity.android.phoenix.events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.phoenixmarketcity.android.phoenix.R;

/**
 * Created by Mansur on 7/4/2015.
 */
public class CardInviteFriendView extends LinearLayout {
    private Button mInviteFriendsButton;
    public static final int PICK_CONTACT=1;
    Context mContext;


    public static CardInviteFriendView newInstance(Context context) {
        CardInviteFriendView card = new CardInviteFriendView(context);
        return card;
    }

    private CardInviteFriendView(final Context context) {
        super(context);
        this.mContext=context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_invites, this, true);

        mInviteFriendsButton=(Button)findViewById(R.id.CardInviteFriendButton);
        mInviteFriendsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Contacts.People.CONTENT_URI);
                ((Activity)mContext).startActivityForResult(intent,PICK_CONTACT);


            }
        });

    }



}
