package com.phoenixmarketcity.android.phoenix.activities;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.util.Preferences;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;
import com.squareup.picasso.Picasso;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 8/7/2015.
 */
public class CommentsActivity extends BaseActivity {

    private String mPageName = "18_Write_Comments";
    private final int CAMERA_LAUNCH_REQUEST_CODE = 101;

    private ViewGroup mParentLayout;
    private ProgressBar mProgressBar;
    private List<CommentData> mCommentData;
    private ListView listView;
    private View mSendButtonView;
    private CardCommentAdapter mCommentListAdapter;
    private EditText mCommentEditText;
    private View mCameraButtonView;
    private LinearLayout mLikeImage;
    private LinearLayout mShareImage;
    private ImageView mEmojiImage;
    private ImageView imageView;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.cardlist);

        listView = (ListView) findViewById(R.id.listParent);

        mCommentEditText = (EditText) findViewById(R.id.WriteCommentEditText);

        mSendButtonView = findViewById(R.id.FooterSend);
        mSendButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mCommentEditText.getText().toString())) {
                    mCommentListAdapter.addComment(createComment(mCommentEditText.getText().toString(), null, null));
                    mCommentEditText.setText("");
                }
            }
        });

        mCameraButtonView = findViewById(R.id.FooterCamera);
        mCameraButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraLaunchIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraLaunchIntent, CAMERA_LAUNCH_REQUEST_CODE);
            }
        });





            WebService.getInstance().getCommentList(mPageName, new Callback<List<CommentData>>() {

            @Override
            public void success(List<CommentData> eventObj, Response response) {
                mCommentData = eventObj;
                mCommentListAdapter = new CardCommentAdapter(CommentsActivity.this, mCommentData);
                mCommentListAdapter.notifyDataSetChanged();
                listView.setTranscriptMode(listView.TRANSCRIPT_MODE_NORMAL);
                listView.setStackFromBottom(true);
                listView.setAdapter(mCommentListAdapter);
            }

                @Override
                public void failure(RetrofitError error) {
                    Logger.d(mPageName + " page loading failed with error: " +
                            error.getResponse() + "/" + error.getMessage() + "/" + error.getBody());

                    String toastStr = String.format(CommentsActivity.this.getResources().getString(
                            R.string.page_layout_fetch_error), mPageName);
                    Toast.makeText(CommentsActivity.this, toastStr, Toast.LENGTH_LONG).show();
                    finish();
                }
            });


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_LAUNCH_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photoBitmap = (Bitmap) data.getExtras().get("data");
            mCommentListAdapter.addComment(createComment(null, photoBitmap, null));
            TextView mCommentText = (TextView) findViewById(R.id.commenttext);
        }
    }

    private CommentData createComment(String commentText, Bitmap mImageBitmap, String mVideoURL) {
        CommentData data = new CommentData();
        data.mUserData = getCurrentUserData();

        CommentData.CardCommentViewTextData commentTextData = new CommentData.CardCommentViewTextData();
        commentTextData.mText = commentText;
        data.mCommentTextData = commentTextData;
        commentTextData.mMediaItems = new ArrayList<CommentData.CardCommentViewTextData.CardMediaViewData>();

        CommentData.CardCommentViewTextData.CardMediaViewData mediaData = new CommentData.CardCommentViewTextData.CardMediaViewData();
        mediaData.mSrc = null;
        mediaData.mBitmap = mImageBitmap;
        mediaData.mMimeType = "image/jpeg";
        commentTextData.mMediaItems.add(mediaData);

        return data;
    }

    private CommentData.CommentUserData getCurrentUserData() {
        CommentData.CommentUserData userData = new CommentData.CommentUserData();
        userData.mName = Preferences.getGooglePlusName();
        userData.mProfileImage = Preferences.getGooglePlusPhotoUrl();
        return userData;
    }

    @Parcel
    public static class CommentData {
        @SerializedName("commentId")
        public String mCommentId;
        @SerializedName("userData")
        public CommentUserData mUserData;
        @SerializedName("comments")
        public CardCommentViewTextData mCommentTextData;

        @Parcel
        public static class CommentUserData {
            @SerializedName("id")
            public String mUserId;
            @SerializedName("time")
            public String mTime;
            @SerializedName("profileImage")
            public String mProfileImage;
            @SerializedName("name")
            public String mName;
        }

        @Parcel
        public  static class CardCommentViewTextData {
            @SerializedName("text")
            public String mText;
            @SerializedName("media")
            public List<CardMediaViewData> mMediaItems;

        @Parcel
            public static class CardMediaViewData {
                @SerializedName("mimetype")
                public String mMimeType;
                @SerializedName("src")
                public String mSrc;
                public Bitmap mBitmap;
            }
        }
    }

    public class CardCommentAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<CommentData> mCommentList;

        //constructor
        public CardCommentAdapter(Context context, List<CommentData> data) {
          //  mInflater = LayoutInflater.from(context);
            mContext=context;
            mCommentList = data;
            mInflater = LayoutInflater.from(context);
        }

        public void addComment(CommentData comment) {
            mCommentList.add(comment);
            notifyDataSetChanged();
        }

        public int getCount() {
            return mCommentList.size();
        }

        @Override
        public Object getItem(int position) {
            return mCommentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public View getView(final int position, View convertView, ViewGroup viewGroup) {


            final ViewHolder holder;
            if(convertView == null) {
                convertView = mInflater.inflate(R.layout.cardview_comment, viewGroup, false);
                holder = new ViewHolder();
                holder.mUserProfileImage = (ImageView)convertView.findViewById(R.id.userprofileimage);
                holder.username = (TextView)convertView.findViewById(R.id.username);
                holder.commenttime = (TextView)convertView.findViewById(R.id.commenttime);
                holder.commenttext = (TextView)convertView.findViewById(R.id.commenttext);
                holder.mMediaView = (ImageView) convertView.findViewById(R.id.commentImage);
                holder.mLikeImage = (LinearLayout) convertView.findViewById(R.id.layoutlike);
                holder.mShareImage = (LinearLayout) convertView.findViewById(R.id.shareLayout);
                holder.mLikeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count++;
                        if(count%2==0){
                            holder.mLikeImage.setSelected(true);
                        Toast.makeText(getApplicationContext(),"You Liked",Toast.LENGTH_SHORT).show();}
                        else{
                            holder.mLikeImage.setSelected(false);
                        Toast.makeText(getApplicationContext(),"You DisLiked",Toast.LENGTH_SHORT).show();}

                    }

                });

                holder.mShareImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Here is the share content body";
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        holder.mShareImage.setSelected(true);

                    }
                });
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            CommentData commentdata = mCommentList.get(position);
            Picasso.with(CommentsActivity.this).load(commentdata.mUserData.mProfileImage).into(holder.mUserProfileImage);
            holder.username.setText(commentdata.mUserData.mName);
            holder.commenttime.setText(commentdata.mUserData.mTime);
            holder.commenttext.setText(commentdata.mCommentTextData.mText);

            holder.username.setText(commentdata.mUserData.mName);
            Picasso.with(CommentsActivity.this).load(commentdata.mUserData.mProfileImage).into(holder.mUserProfileImage);
            holder.commenttime.setText("1 day ago");

            if (commentdata.mCommentTextData.mMediaItems != null && commentdata.mCommentTextData.mMediaItems.size() > 0 &&
                    commentdata.mCommentTextData.mMediaItems.get(0).mSrc != null) {
                Picasso.with(CommentsActivity.this).load(commentdata.mCommentTextData.mMediaItems.get(0).mSrc).into(holder.mMediaView);
            } else {
                holder.mMediaView.setImageBitmap(commentdata.mCommentTextData.mMediaItems.get(0).mBitmap);
            }
            return convertView;
        }

         private class ViewHolder {
            public ImageView mUserProfileImage;
            public LinearLayout mLikeImage;
            public LinearLayout mShareImage;
            public TextView username;
             public TextView commenttext;
             public TextView commenttime;
             public ImageView mMediaView;
         }
    }
}


/*
final Button home=(Button)findViewById(R.id.FooterButtonHome);
final LinearLayout store=(LinearLayout)findViewById(R.id.zara_footer);
final Button Do =(Button)findViewById(R.id.FooterButtonDo);
home.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent i = new Intent(CommentsActivity.this, LandingPageActivity.class);
        startActivity(i);
        }
        });
        store.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent i = new Intent(CommentsActivity.this, Dummypages.class);
        startActivity(i);
        }
        });
        Do.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent i = new Intent(CommentsActivity.this, DoActivity.class);
        startActivity(i);
        }
        });*/
