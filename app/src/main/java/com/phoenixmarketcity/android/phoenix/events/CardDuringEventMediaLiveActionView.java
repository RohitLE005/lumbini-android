package com.phoenixmarketcity.android.phoenix.events;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.CommentsActivity;
import com.phoenixmarketcity.android.phoenix.views.ExpandableHeightGridView;

/**
 * Created by Mansur on 7/23/2015.
 */
public class CardDuringEventMediaLiveActionView extends LinearLayout {

    int count = 0;
    public static CardDuringEventMediaLiveActionView newInstance(Context context) {
        CardDuringEventMediaLiveActionView card = new CardDuringEventMediaLiveActionView(context);
        return card;
    }

    private CardDuringEventMediaLiveActionView(final Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_eventduring_live_action, this, true);

        ExpandableHeightGridView eventMedia = (ExpandableHeightGridView) findViewById(R.id.cardEventMedia);
        // Instance of ImageAdapter Class
        eventMedia.setAdapter(new ImageAdapter(context));
        eventMedia.setExpanded(true);

        final TextView liveActionText = (TextView) findViewById(R.id.cardEventDuringLiveText);
        final TextView mediaText = (TextView) findViewById(R.id.cardEventDuringMediaText);

        final LinearLayout liveActionLayout = (LinearLayout) findViewById(R.id.cardLiveActionLayout);
        final LinearLayout mediaLayout = (LinearLayout) findViewById(R.id.cardMediaLayout);
        /* setting of comment */
        final ImageView commentText = (ImageView) findViewById(R.id.commentIcon);
        commentText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), CommentsActivity.class);
                context.startActivity(intent);
            }
        });

        final ImageView likeInfo = (ImageView) findViewById(R.id.icon_Like);
        likeInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count % 2 == 0) {
                    likeInfo.setSelected(true);
                    Toast.makeText(getContext(), "You Liked", Toast.LENGTH_LONG).show();
                } else {
                    likeInfo.setSelected(false);
                    Toast.makeText(getContext(), "You Disliked", Toast.LENGTH_LONG).show();
                }
            }
        });

        /* setting of Share Information */
        ImageView sharingInfo = (ImageView) findViewById(R.id.shareIcon);
        sharingInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        liveActionText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == 1) {
                    liveActionLayout.setVisibility(VISIBLE);
                    mediaLayout.setVisibility(GONE);
                }
                mediaText.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count--;
                        if (count == 0)
                            mediaLayout.setVisibility(VISIBLE);
                        liveActionLayout.setVisibility(GONE);
                    }
                });
            }
        });


        ImageView rimage = (ImageView) findViewById(R.id.rimage1);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_1x2);
        rimage.setImageBitmap(icon);

        ImageView flagIcon = (ImageView) findViewById(R.id.cardEventFlag);
        flagIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Content inside dialog */
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_report_post);
                dialog.setTitle("REPORT THIS POST");
                dialog.show();

                TextView flagPost1 = (TextView) dialog.findViewById(R.id.eventPost1);
                flagPost1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "I don't like this post", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                TextView flagPost2 = (TextView) dialog.findViewById(R.id.eventPost2);
                flagPost2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "This post is spam or scam", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                TextView flagPost3 = (TextView) dialog.findViewById(R.id.eventPost3);
                flagPost3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "This post puts people at risk", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                TextView flagPost4 = (TextView) dialog.findViewById(R.id.eventPost4);
                flagPost4.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "This post should be an adult", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    // Image Adapter class
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        // Keep all Images in array
        public Integer[] mThumbIds = {
                R.drawable.brand_1, R.drawable.brand_2,
                R.drawable.brand_3, R.drawable.brand_4,
                R.drawable.brand_5, R.drawable.brand_6
        };

        // Constructor
        public ImageAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mThumbIds[position]);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            return imageView;
        }
    }
}
