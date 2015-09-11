package com.phoenixmarketcity.android.phoenix.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.EventDetailsActivity;

/**
 * Created by Lumbini on 04-08-2015.
 */
public class CardPostAnimationView extends LinearLayout {

    public static CardPostAnimationView newInstance(Context context, EventDetailsActivity.EventDescriptionData eventData) {
        CardPostAnimationView card = new CardPostAnimationView(context, eventData);
        return card;

    }

    private CardPostAnimationView(final Context context, EventDetailsActivity.EventDescriptionData eventData) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_post_animation, this, true);

      /*  VideoView videoView = (VideoView) findViewById(R.id.videoView);*/
       /* MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);*/
       /* videoView.setVideoPath(eventData.mEventDetails.mUrlVideo);
        videoView.start();*/

       /* videoView.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.a));
        videoView.start();*/

//        TextView eventTitle = (TextView) findViewById(R.id.eventTextTitle);
//        eventTitle.setText(eventData.mEventDetails.mEventTitleText);
//
//        TextView eventGreet = (TextView) findViewById(R.id.eventTextGreet);
//        eventGreet.setText(eventData.mEventDetails.mEventGreetText);

    }

}
